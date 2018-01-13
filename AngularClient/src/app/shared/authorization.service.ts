import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Subject } from 'rxjs/Subject';
import { User } from '../user/user';

@Injectable()
export class AuthorizationService {

  private login: string = null;
  private password: string = null;
  private authenticator: User = null;
  public authorized$ = new BehaviorSubject<boolean>(false);
  public authenticator$ = new Subject<any>();

  // store the URL so we can redirect after logging in
  public redirectUrl: string;

  constructor() {
    this.restoreAuthorization();
  }

  public hasAuthorization(): boolean {
    return this.login !== null && this.password !== null;
  }

  get isLoggedIn() {
    return this.authorized$.asObservable(); // {2}
  }

  public hasRole(role): boolean {
    return this.authenticator.role === role;
  }

  public setAuthorization(login: string, password: string): void {
    this.login = login;
    this.password = password;
  }

  public storeAuthorization(authenticator: User, local: boolean) {
    this.authenticator = authenticator;

    const authorization = {
      login: this.login,
      password: this.password,
      authenticator: this.authenticator
    };

    const authorizationString = JSON.stringify(authorization);
    const storage = localStorage;

    storage.setItem('authorization', authorizationString);

    this.authenticator$.next({
      authorized: true,
      role: this.authenticator.role
    });

    this.authorized$.next(true);
  }

  private restoreAuthorization(): void {
    let authorizationString = sessionStorage.getItem('authorization');

    if (authorizationString === null) {
      authorizationString = localStorage.getItem('authorization');
    }

    if (authorizationString !== null) {
      const authorization = JSON.parse(authorizationString);

      this.login = authorization['login'];
      this.password = authorization['password'];
      this.authenticator = authorization['authenticator'];

      this.authenticator$.next({
        authorized: true,
        role: this.authenticator.role
      });

      this.authorized$.next(true);
    }
  }

  public deleteAuthorization(): void {
    this.login = null;
    this.password = null;
    this.authenticator = null;

    sessionStorage.removeItem('authorization');
    localStorage.removeItem('authorization');

    this.authenticator$.next({
      authorized: true,
      role: null
    });

    this.authorized$.next(false);
  }

  public createAuthorizationString(): string {
    return 'Basic ' + btoa(this.login + ':' + this.password);
  }

  public getAuthenticator(): User {
    return this.authenticator;
  }

  public setAuthenticator(authenticator: User): void {
    this.authenticator = authenticator;
  }
}
