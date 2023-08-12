import {Validators} from "@angular/forms";

export class History {
  public id: string | undefined;
  public createdAt: string | undefined;
  public type: string | undefined;
  public amount: string | undefined;
  public compteId: string | undefined | null;

  constructor(id?: string, createdAt?: string, type?: string, amount?: string) {
    this.id = id;
    this.createdAt = createdAt;
    this.type = type;
    this.amount = amount;
  }


  public static createValidation(): Validators {

    return {
      'type': [null],
      'amount': [null],
    }
  }
}
