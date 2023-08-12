import { Component, OnInit } from '@angular/core';
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html'
})
export class UserComponent implements OnInit {
  items: MenuItem[] = [];
  constructor() {

  }

  ngOnInit() {
    this.items = [
      {label: 'Create new account'},
      {label: 'List account'}
    ];
  }

}
