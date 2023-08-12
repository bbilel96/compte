import {RequestValidation} from "./request-validation";

export class ErrorMessage {
  private _message: string | undefined;
  private _validations: RequestValidation[] = [];

  constructor(message?: string, validations?: any) {
    this._message = message;
    this._validations = validations;
  }

  get message(): string | undefined {
    return this._message;
  }

  set message(value: string | undefined) {
    this._message = value;
  }

  get validations(): RequestValidation[] {
    return this._validations;
  }

  set validations(value: RequestValidation[]) {
    this._validations = value;
  }
}
