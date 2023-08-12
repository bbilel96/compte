import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment";
import {ResponseMessage} from "../../util/model/response-message/response-message";
import {Observable} from "rxjs";
import {User} from "../../model/user/user";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  public login(user: User): Observable<any> {

    const body = new HttpParams()
      .set('email', user.email)
      .set('password', user.password);
    return this.http.post<any>(environment.URL + 'login', body.toString(),{
      withCredentials: true,
      headers:  new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded'),

    });
  }
  public createUser(user: User): Observable<ResponseMessage> {

    return this.http.post<ResponseMessage>(environment.URL + 'user', user,{
      withCredentials: true,
    });
  }
  public getUserByEmail(user: User): Observable<User> {

    return this.http.get<User>(environment.URL + 'user/email/'+user.email,{
      withCredentials: true,
    });
  }
  public editUser(id: string, user: User): Observable<ResponseMessage> {

    return this.http.put<ResponseMessage>(environment.URL + 'user/'+ id, user,{
      withCredentials: true,
    });

  }
  public getUserById(id: string | null | undefined): Observable<User> {

    return this.http.get<User>(environment.URL + 'user/'+ id,{
      withCredentials: true,
    });

  }
}
