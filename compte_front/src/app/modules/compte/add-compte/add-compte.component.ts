import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ResponseMessage} from "../../../@core/util/model/response-message/response-message";
import {Loading} from "../../../@core/util/model/loading/loading";
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {Compte} from "../../../@core/model/compte/compte";
import {CompteService} from "../../../@core/service/compte-service/compte.service";
import {RequestValidation} from "../../../@core/util/model/response-message/request-validation";

@Component({
  selector: 'app-add-compte',
  templateUrl: './add-compte.component.html'
})
export class AddCompteComponent implements OnInit, AfterViewInit, OnDestroy {

  addCompte: FormGroup;
  responseMessage: ResponseMessage = new ResponseMessage();
  loading: Loading = new Loading("pi pi-check");
  userId: string | undefined | null;
  private subscription = new Subscription();

  constructor(private formBuilder: FormBuilder, private router: Router, private messageService: MessageService, private compteService: CompteService, private activatedRoute: ActivatedRoute) {
    this.addCompte = this.formBuilder.group(Compte.createValidation());
  }

  ngAfterViewInit() {
    this.userId = this.activatedRoute.snapshot.paramMap.get('id');
  }

  ngOnInit(): void {
  }

  submit() {
    let compte: Compte = this.addCompte.getRawValue();
    compte.userId = this.userId;
    this.subscription.add(
      this.compteService.createCompte(compte).subscribe(data => {
          this.loading.success(data.message, '', this.messageService);

        },
        (error: ResponseMessage) => {
          this.responseMessage = error;
          this.addCompte.enable();
          this.loading.error("pi pi-check", this.messageService, this.responseMessage);
        },
        () => {
          this.responseMessage = new ResponseMessage();
          this.addCompte.enable();
          this.addCompte.reset();
        }
      )
    )
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
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
