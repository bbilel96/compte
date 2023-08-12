import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ResponseMessage} from "../../util/model/response-message/response-message";
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Compte} from "../../model/compte/compte";
import {History} from "../../model/history/history";

@Injectable({
  providedIn: 'root'
})
export class CompteService {

  constructor(private http: HttpClient) {
  }

  public getAllByUserId(userid: string | null | undefined, page: number, size: number): Observable<any> {
    return this.http.get<ResponseMessage>(environment.URL + 'compte/' + userid + "/" + page + "/" + size, {withCredentials: true});
  }

  public createCompte(compte: Compte): Observable<any> {
    return this.http.post<ResponseMessage>(environment.URL + 'compte/', compte, {withCredentials: true});
  }

  public updateCompte(id: string | null | undefined, compte: Compte): Observable<any> {
    return this.http.put<ResponseMessage>(environment.URL + 'compte/' + id, compte, {withCredentials: true});
  }

  public getById(id: string | null | undefined): Observable<Compte> {
    return this.http.get<Compte>(environment.URL + "compte/" + id, {withCredentials: true});
  }

  delete(id: number) {
    return this.http.delete<ResponseMessage>(environment.URL + "compte/" + id, {withCredentials: true});
  }
  public getAllByCompteId(compteId: string | null | undefined, page: number, size: number): Observable<any> {
    return this.http.get<ResponseMessage>(environment.URL + 'history/' + compteId + "/" + page + "/" + size, {withCredentials: true});
  }
  public createHistory(compteId: String | null | undefined, history: History): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(environment.URL + 'history/'+ compteId +"/", history , {withCredentials: true});
  }
}
