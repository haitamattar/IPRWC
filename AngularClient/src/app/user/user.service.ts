import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { User } from './user';
import { ApiService } from '../shared/api.service';
import { AuthorizationService } from '../shared/authorization.service';

@Injectable()
export class UserService {
    constructor(private api: ApiService, private authService: AuthorizationService, private router: Router) {

    }

    public getAll(): Observable<User[]> {
        return this.api.get<User[]>('users');
    }

    public create(user: User): Observable<User> {
        return this.api.post<User>('users/add', user);
    }

    public login(user: User) {
        this.authService.setAuthorization(user.email, user.password);
        return this.api.get<User>('users/me');
    }

    public logout() {
        this.authService.deleteAuthorization();
        this.goHome();
    }

  private goHome() {
    this.router.navigate(['home']);
  }

  storeAuth(authenticator, remember) {
        this.authService.storeAuthorization(authenticator, remember);
    }
}
