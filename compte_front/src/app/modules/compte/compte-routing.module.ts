import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ListeCompteComponent} from "./liste-compte/liste-compte.component";
import {AddCompteComponent} from "./add-compte/add-compte.component";
import {UpdateCompteComponent} from "./update-compte/update-compte.component";
import {DetailCompteComponent} from "./detail-compte/detail-compte.component";
import {AuthGuard} from "../../@core/guards/auth/auth.guard";

const routes: Routes = [
  {
    path: 'list/:id',
    component: ListeCompteComponent,
    canActivate:[AuthGuard]
  },
  {
    path: 'create/:id',
    component: AddCompteComponent,
    canActivate:[AuthGuard]
  },
  {
    path: 'update/:id/:compteId',
    component: UpdateCompteComponent,
    canActivate:[AuthGuard]
  },
  {
    path: 'detail/:id/:compteId',
    component: DetailCompteComponent,
    canActivate:[AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompteRoutingModule { }
