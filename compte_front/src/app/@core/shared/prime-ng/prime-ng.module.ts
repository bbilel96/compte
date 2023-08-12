import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { MenubarModule } from 'primeng/menubar';
import { MegaMenuModule } from 'primeng/megamenu';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { CardModule } from 'primeng/card';
import { ConfirmationService, MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { SkeletonModule } from 'primeng/skeleton';
import { PaginatorModule } from 'primeng/paginator';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { MultiSelectModule } from 'primeng/multiselect';
import {StepsModule} from 'primeng/steps';
import {TabMenuModule} from 'primeng/tabmenu';

@NgModule({
  imports: [
    CommonModule,
    InputTextModule,
    ButtonModule,
    RippleModule,
    MenubarModule,
    MegaMenuModule,
    BreadcrumbModule,
    AvatarModule,
    AvatarGroupModule,
    CardModule,
    MessageModule,
    MessagesModule,
    TableModule,
    DialogModule,
    ToastModule,
    SkeletonModule,
    PaginatorModule,
    ConfirmDialogModule,
    InputTextareaModule,
    MultiSelectModule,
    StepsModule,
    TabMenuModule
  ],
  exports: [
    InputTextModule,
    ButtonModule,
    RippleModule,
    MenubarModule,
    MegaMenuModule,
    DialogModule,
    AvatarModule,
    BreadcrumbModule,
    AvatarGroupModule,
    CardModule,
    MessageModule,
    MessagesModule,
    TableModule,
    ToastModule,
    SkeletonModule,
    PaginatorModule,
    ConfirmDialogModule,
    InputTextareaModule,
    MultiSelectModule,
    StepsModule,
    TabMenuModule
  ],
})
export class PrimeNgModule {}
