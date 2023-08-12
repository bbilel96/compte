import {User} from "../user/user";
import {FormControl, Validators} from "@angular/forms";

export class Compte {
  public id: string | undefined;
  public maxBalance: number | undefined;
  public totalBalance: number | undefined;
  public userId: string | null | undefined;
  public user: User | undefined;

  constructor(id?: string, maxBalance?: number, totalBalance?: number, userId?: string | null | undefined, user?: User) {
    this.id = id;
    this.maxBalance = maxBalance;
    this.totalBalance = totalBalance;
    this.userId = userId;
    this.user = user;
  }


  public static createValidation(): Validators {

    return {
      'maxBalance': [null],
    }
  }
  public static updateValidation(): Validators {

    return {
      maxBalance: new FormControl <number | null>(null),
      totalBalance: new FormControl <number | null>({value: null, disabled: true}),
    }
  }
}
