import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { Loading } from '../../../@core/util/model/loading/loading';
import { ResponseMessage } from '../../../@core/util/model/response-message/response-message';
import { Subscription } from 'rxjs';
import { Compte } from '../../../@core/model/compte/compte';
import { CompteService } from '../../../@core/service/compte-service/compte.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-liste-compte',
  templateUrl: './liste-compte.component.html',
})
export class ListeCompteComponent implements OnInit, AfterViewInit, OnDestroy {
  listComptes: Compte[] = [];
  totalPage: number = 0;
  responseMessage: ResponseMessage = new ResponseMessage();
  loading: Loading = new Loading('');
  private subscription = new Subscription();
  display: boolean = false;
  userId: string | null | undefined;

  constructor(
    private compteService: CompteService,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private activatedRoute: ActivatedRoute,
    private changeDet: ChangeDetectorRef
  ) {}

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {}

  ngAfterViewInit() {
    this.userId = this.activatedRoute.snapshot.paramMap.get('id');
    this.getAllComptes();
  }

  private getAllComptes() {
    this.loading.loading = true;
    this.changeDet.detectChanges();
    this.subscription.add(
      this.compteService.getAllByUserId(this.userId, 0, 5).subscribe(
        (data) => {
          this.listComptes = data.content;

          this.totalPage = data.totalElements;
        },
        (error) => {
          this.responseMessage = error;
          this.loading.error('', this.messageService, this.responseMessage);
        },
        () => {
          this.responseMessage = new ResponseMessage();
          this.loading.success('', '', this.messageService);
        }
      )
    );
  }

  paginate($event: any) {
    this.loading.loading = true;
    this.subscription.add(
      this.compteService
        .getAllByUserId(this.userId, $event.page + 0, $event.rows)
        .subscribe(
          (data) => {
            this.listComptes = data.content;
          },
          (error) => {
            this.responseMessage = error;
            this.loading.error('', this.messageService, this.responseMessage);
          },
          () => {
            this.loading.success('', '', this.messageService);
          }
        )
    );
  }

  delete(id: number) {
    this.confirmationService.confirm({
      message: 'Your are going to delete a compte. Are you sure ?',
      header: 'Are you sure ?',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.deleteCompte(id);
      },
    });
  }

  private deleteCompte(id: number) {
    this.loading.loading = true;
    this.subscription.add(
      this.compteService.delete(id).subscribe(
        (data) => {
          this.loading.success(data.message, '', this.messageService);
        },
        (error) => {
          this.responseMessage = error;
          this.loading.error('', this.messageService, this.responseMessage);
        },
        () => {
          this.getAllComptes();
        }
      )
    );
  }

  update(id: String) {
    this.router.navigate([
      '/user/main/comptes/update/' + localStorage.getItem('userId') + '/' + id,
    ]);
  }

  details(id: string) {
    this.router.navigate([
      '/user/main/comptes/detail/' + localStorage.getItem('userId') + '/' + id,
    ]);
  }
}
