import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {ResponseMessage} from "../../../@core/util/model/response-message/response-message";
import {CompteService} from "../../../@core/service/compte-service/compte.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {ActivatedRoute} from "@angular/router";
import {Compte} from "../../../@core/model/compte/compte";
import {Loading} from "../../../@core/util/model/loading/loading";
import {Subscription} from "rxjs";
import {History} from "../../../@core/model/history/history";
import {FormBuilder, FormGroup} from "@angular/forms";
import {RequestValidation} from "../../../@core/util/model/response-message/request-validation";

@Component({
  selector: 'app-detail-compte',
  templateUrl: './detail-compte.component.html'
})
export class DetailCompteComponent implements OnInit,OnDestroy {
  listHistory: History[] = [];
  displayModal: boolean = false;
  totalPage: number = 0;
  responseMessage: ResponseMessage = new ResponseMessage();
  loading: Loading = new Loading('');
  private subscription = new Subscription();
  display: boolean = false;
  compteId: string | null ;
  selectedCompte: Compte = new Compte();
  addHistory: FormGroup;
  types: any[] = [];
  constructor(private compteService: CompteService,
              private messageService: MessageService,
              private confirmationService: ConfirmationService,
              private activatedRoute: ActivatedRoute,
              private changeDet: ChangeDetectorRef,
              private formBuilder: FormBuilder) {
    this.addHistory = this.formBuilder.group(History.createValidation());
    this.compteId = this.activatedRoute.snapshot.paramMap.get('compteId');
  }

  ngOnInit(): void {
    this.types = [
      {name: 'Withdraw', code: 'WITHDRAW'},
      {name: 'Depose', code: 'DEPOSE'},
    ];
  }
  ngAfterViewInit() {

    this.getAllComptes();
    this.getCompteById(this.compteId)
  }
  private getAllComptes() {
    this.loading.loading = true;
    this.changeDet.detectChanges();
    this.subscription.add(this.compteService.getAllByCompteId(this.compteId, 0, 5).subscribe(data => {
        this.listHistory = data.content;

        this.totalPage = data.totalElements;
      },
      error => {
        this.responseMessage = error;
        this.loading.error('', this.messageService, this.responseMessage)
      },
      () => {
        this.responseMessage = new ResponseMessage();
        this.loading.success('', '', this.messageService);
      }));
  }

  paginate($event: any) {
    this.loading.loading = true;
    this.subscription.add(this.compteService.getAllByCompteId(this.compteId, $event.page + 0, $event.rows).subscribe(data => {
        this.listHistory = data.content;
      },
      error => {
        this.responseMessage = error;
        this.loading.error('', this.messageService, this.responseMessage)

      },
      () => {
        this.loading.success('', '', this.messageService);
      }));
  }
  getCompteById(id: string | null | undefined) {

    this.compteService.getById(id).subscribe(data => {

      this.selectedCompte = data;


      },
      (error: ResponseMessage) => {
        this.responseMessage = error;

        this.loading.error("pi pi-check", this.messageService, this.responseMessage);
      },
      () => {
        this.responseMessage = new ResponseMessage();
      }
    );
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  showModalDialog() {
    this.displayModal = true;
  }

  submit() {

    let history: History = new History();
    history.type = this.addHistory.get("type")?.value?.code;
    history.amount = this.addHistory.get("amount")?.value;
    history.compteId = this.compteId
    console.log(history)

    this.subscription.add(
      this.compteService.createHistory(this.compteId, history).subscribe(data => {
          this.loading.success(data.message, '', this.messageService);

        },
        (error: ResponseMessage) => {
          this.responseMessage = error;
          this.addHistory.enable();
          this.loading.error("pi pi-check", this.messageService, this.responseMessage);
        },
        () => {
          this.responseMessage = new ResponseMessage();
          this.addHistory.enable();
          this.addHistory.reset();
          this.getAllComptes();
          this.getCompteById(this.compteId)
          this.displayModal = false;
        }
      )
    )
  }
  inputError(validations: RequestValidation[] , name: string): RequestValidation[] {
    let result: RequestValidation
    if (validations == null){
      return [];
    }
    return validations.filter(validation =>
      validation.name == name
    );

  }
}
