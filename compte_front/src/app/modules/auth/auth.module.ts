import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {PrimeNgModule} from "../../@core/shared/prime-ng/prime-ng.module";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [AuthComponent, LoginComponent, SignupComponent],
  imports: [CommonModule, AuthRoutingModule, PrimeNgModule, ReactiveFormsModule],
})
export class AuthModule {}
