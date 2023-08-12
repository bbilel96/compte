import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit, AfterViewInit {

  items: MenuItem[] = [];
  userId: String | null | undefined;
  constructor(private route: Router, private confirmationService: ConfirmationService,  private messageService: MessageService) {
  }
ngAfterViewInit() {
    this.userId = localStorage.getItem("userId");
}

  ngOnInit() {
    this.items = [
      {label: 'Create new account'},
      {label: 'List account'}
    ];
  }

  navigateAccount() {
    console.log(this.userId)
    this.route.navigate(["/user/main/comptes/create/"+ this.userId])
  }
  navigateList() {
    console.log(this.userId)
    this.route.navigate(["/user/main/comptes/list/"+ this.userId])
  }

  logout() {
    this.confirmationService.confirm({
      message: 'You are going to logout, are you sure?',
      header: 'Are you sure ?',
      icon: 'pi pi-info-circle',
      accept: () => {
        localStorage.removeItem("userId")
        localStorage.removeItem("assess_token")
        this.route.navigate(["/auth/login"])
      }
    });
  }
}
