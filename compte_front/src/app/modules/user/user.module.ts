import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import {PrimeNgModule} from "../../@core/shared/prime-ng/prime-ng.module";
import {LayoutModuleModule} from "../../@core/shared/layout-module/layout-module.module";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    UserComponent,

  ],
    imports: [
        CommonModule,
        UserRoutingModule,
        PrimeNgModule,
        LayoutModuleModule,
        ReactiveFormsModule
    ]
})
export class UserModule { }
