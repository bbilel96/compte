import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ResponseMessage} from "../../../@core/util/model/response-message/response-message";
import {Loading} from "../../../@core/util/model/loading/loading";
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {User} from "../../../@core/model/user/user";
import {UserService} from "../../../@core/service/user-service/user.service";
import {RequestValidation} from "../../../@core/util/model/response-message/request-validation";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit, OnDestroy {
  login: FormGroup;
  responseMessage: ResponseMessage = new ResponseMessage();
  loading: Loading = new Loading("pi pi-check");
  userId: string | undefined | null;
  private subscription = new Subscription();

  constructor(private formBuilder: FormBuilder, private router: Router, private messageService: MessageService, private userService: UserService, private activatedRoute: ActivatedRoute) {
    this.login = this.formBuilder.group(User.createValidation());
  }

  ngOnInit(): void {
  }

  submit() {

    let user: User = this.login.getRawValue();
    this.subscription.add(
      this.userService.login(user).subscribe(data => {
          localStorage.setItem("assess_token", data.access_token)
          localStorage.setItem("expired", data.expired)
          console.log(data)
          this.loading.success(data.message, '', this.messageService);
        },
        (error: ResponseMessage) => {
          this.messageService.clear();
          this.messageService.add({
            severity: 'error',
            summary: '',
            detail: "Email or Password incorrect.",
            sticky: true
          });
          this.login.enable();
        },
        () => {
          this.responseMessage = new ResponseMessage();
          this.login.enable();
          this.login.reset();
          this.getUserByEmail(user);
        }
      )
    )
  }

  getUserByEmail(user: User) {
    this.userService.getUserByEmail(user).subscribe(data => {
      console.log(data);
      localStorage.setItem("userId", data.id);
      this.router.navigate(["/user/main/comptes/list/" + localStorage.getItem("userId")])
    }, (error: ResponseMessage) => {
      this.responseMessage = error;
    },);
  }

  inputError(validations: RequestValidation[], name: string): RequestValidation[] {
    let result: RequestValidation
    if (validations == null) {
      return [];
    }
    return validations.filter(validation =>
      validation.name == name
    );

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
