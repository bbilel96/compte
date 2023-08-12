import {Validators} from "@angular/forms";

export class User {
  private _id: string;
  private _firstName: string;
  private _lastName: string;
  private _phoneNumber: string;
  private _password: string;
  private _email: string;


  constructor(id: string, firstName: string, lastName: string, phoneNumber: string, password: string, email: string) {
    this._id = id;
    this._firstName = firstName;
    this._lastName = lastName;
    this._phoneNumber = phoneNumber;
    this._password = password;
    this._email = email;
  }

  get id(): string {
    return this._id;
  }

  set id(value: string) {
    this._id = value;
  }

  get firstName(): string {
    return this._firstName;
  }

  set firstName(value: string) {
    this._firstName = value;
  }

  get lastName(): string {
    return this._lastName;
  }

  set lastName(value: string) {
    this._lastName = value;
  }

  get phoneNumber(): string {
    return this._phoneNumber;
  }

  set phoneNumber(value: string) {
    this._phoneNumber = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }


  public static createValidation(): Validators {

    return {
      'firstName': [null],
      'lastName': [null],
      "phoneNumber": [null],
      "password": [null],
      "email": [null]
    }
  } public static login(): Validators {

    return {
      "email": [null],
      "password": [null],

    }

  }
}
