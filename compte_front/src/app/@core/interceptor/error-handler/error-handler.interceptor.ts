import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, retry, throwError} from 'rxjs';
import {ResponseMessage} from "../../util/model/response-message/response-message";
import {Behavior} from "../../util/const/Behavior";

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
      .pipe(
        catchError((er: HttpErrorResponse) => {
          return throwError(this.errorHandler(er))
        })
      )

  }
  errorHandler(er: HttpErrorResponse): ResponseMessage {
    let response: ResponseMessage = new ResponseMessage();

    console.log(er)
    response.status = er.status;

    switch (er.status) {
      case 422:
        response.error.message = er.message;
        response.error.validations = er.error
        break;
      case 400:
        response.error.message = er.error.message;
        response.error.validations = er.error.validations;
        break
      case 500:
        response.error.message = "Quelque chose s'est mal passé.";
        break;
      case 0:
        response.error.message = "Connexion echoué avec le serveur.";
        break;
      default:
        response.error.message = er.error.message;
    }
    response.behavior = Behavior.FAIL;
    return response;
  }
}
