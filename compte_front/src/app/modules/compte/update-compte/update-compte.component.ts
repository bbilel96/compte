import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {RequestValidation} from "../../../@core/util/model/response-message/request-validation";
import {Compte} from "../../../@core/model/compte/compte";
import {ResponseMessage} from "../../../@core/util/model/response-message/response-message";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {CompteService} from "../../../@core/service/compte-service/compte.service";
import {Loading} from "../../../@core/util/model/loading/loading";
import {Subscription} from "rxjs";
import {User} from "../../../@core/model/user/user";

@Component({
  selector: 'app-update-compte',
  templateUrl: './update-compte.component.html'
})
export class UpdateCompteComponent implements OnInit, OnDestroy {

  updateCompte: FormGroup;
  responseMessage: ResponseMessage = new ResponseMessage();
  loading: Loading = new Loading("pi pi-check");
  userId: string | undefined | null;
  compteId: string | undefined | null;
  private subscription = new Subscription();
  selectedCompte: Compte = new Compte();

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private messageService: MessageService,
              private compteService: CompteService,
              private activatedRoute: ActivatedRoute,
  ) {
    this.updateCompte = this.formBuilder.group(Compte.updateValidation());
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.userId = params.get('id');
      this.compteId = params.get('compteId');
      this.getCompteById(this.compteId);
    });
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
  ngAfterViewInit() {



  }
  submit() {
    this.updateCompte.disable();

    this.selectedCompte.userId = this.userId;
    this.subscription.add(
      this.compteService.updateCompte(this.compteId, this.selectedCompte).subscribe(data => {
          this.loading.success(data.message, '', this.messageService);

        },
        (error: ResponseMessage) => {
          this.responseMessage = error;
          this.updateCompte.enable();
          this.loading.error("pi pi-check", this.messageService, this.responseMessage);
        },
        () => {
          this.responseMessage = new ResponseMessage();
          this.updateCompte.enable();
          this.updateCompte.reset();
        }
      )
    )
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  getCompteById(id: string | null | undefined){
    this.updateCompte.disable();
    this.compteService.getById(id).subscribe(data => {
        this.selectedCompte = data;
        this.updateCompte.get("maxBalance")?.setValue(data.maxBalance)
       // this.selectedCompte.maxBalance = 12;
        console.log(this.selectedCompte)
      },
      (error: ResponseMessage) => {
        this.responseMessage = error;
        this.updateCompte.enable();
        this.loading.error("pi pi-check", this.messageService, this.responseMessage);
      },
      () => {
        this.responseMessage = new ResponseMessage();
        this.updateCompte.enable();
        this.updateCompte.get('totalBalance')?.disable();
        this.updateCompte.reset();
      }
    );
}

}
