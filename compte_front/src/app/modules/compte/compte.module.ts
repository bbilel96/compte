import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompteRoutingModule } from './compte-routing.module';
import {PrimeNgModule} from "../../@core/shared/prime-ng/prime-ng.module";
import { ListeCompteComponent } from './liste-compte/liste-compte.component';
import { AddCompteComponent } from './add-compte/add-compte.component';
import { UpdateCompteComponent } from './update-compte/update-compte.component';
import {ReactiveFormsModule} from "@angular/forms";
import { DetailCompteComponent } from './detail-compte/detail-compte.component';
import {RadioButtonModule} from "primeng/radiobutton";


@NgModule({
  declarations: [
    ListeCompteComponent,
    AddCompteComponent,
    UpdateCompteComponent,
    DetailCompteComponent
  ],
    imports: [
        CommonModule,
        CompteRoutingModule,
        PrimeNgModule,
        ReactiveFormsModule,
        RadioButtonModule
    ]
})
export class CompteModule { }
