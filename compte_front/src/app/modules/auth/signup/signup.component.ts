import {Component, OnDestroy, OnInit} from '@angular/core';
import {MenuItem, MessageService} from "primeng/api";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";
import {Loading} from "../../../@core/util/model/loading/loading";
import {ResponseMessage} from "../../../@core/util/model/response-message/response-message";
import {User} from "../../../@core/model/user/user";
import {Router} from "@angular/router";
import {UserService} from "../../../@core/service/user-service/user.service";
import {RequestValidation} from "../../../@core/util/model/response-message/request-validation";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html'
})
export class SignupComponent implements OnInit, OnDestroy {
  signupForm: FormGroup;
  responseMessage: ResponseMessage = new ResponseMessage();
  loading: Loading = new Loading("pi pi-lock-open");
  private subscription = new Subscription();

  constructor(private formBuilder: FormBuilder, private router: Router, private messageService: MessageService, private userService: UserService) {
    this.signupForm = this.formBuilder.group(User.createValidation());
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    }


  ngOnInit(): void {

  }

  submit(): void {
    let user: User = this.signupForm.getRawValue();
    this.loading.loading = true;
    this.loading.spinner = "pi pi-spin pi-spinner";
    this.signup(user);

  }

  signup(user: User) {
    this.signupForm.disable();
    this.subscription.add(this.userService.createUser(user).subscribe(data => {
      },
      (error: ResponseMessage) => {
        this.responseMessage = error;
        this.signupForm.enable();
        this.loading.error("", this.messageService, this.responseMessage);
      },
      () => {
        this.responseMessage = new ResponseMessage();
        this.signupForm.enable();
        this.loading.success('Bienvenue', 'pi pi-lock-open', this.messageService)
        this.router.navigateByUrl('auth/login')
        this.signupForm.reset();
      }
    ));


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
