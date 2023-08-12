import {ErrorMessage} from "./error-message";
import {Behavior} from "../../const/Behavior";
import {Data} from "@angular/router";

export class ResponseMessage {
  public status: number | undefined;
  public behavior: Behavior | undefined;
  public message: string | undefined;
  public error: ErrorMessage = new ErrorMessage();

  constructor(message?: string, status?: number | undefined, errors?: ErrorMessage | undefined, data?: Data | Data[] | undefined, behavior?: Behavior) {
    this.status = status;
    this.behavior = behavior;
    if (errors != undefined) {
      this.error = errors;
    }
    this.message = message;
  }
}
