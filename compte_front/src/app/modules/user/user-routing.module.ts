import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserComponent} from "./user.component";
import {AuthGuard} from "../../@core/guards/auth/auth.guard";

const routes: Routes = [
  {
    path: 'main',
    component: UserComponent,
    children:[
      {
        path: 'comptes',
        loadChildren: () => import('../compte/compte.module').then(m => m.CompteModule),
        canActivate:[AuthGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
