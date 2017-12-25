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

    public register(user: User): void  {
        const registerData = {
            email: user.email,
            password: user.password,
            fullname: user.fullname,
            postalcode: user.postalcode,
            streetnumber: user.streetnumber
        };

        this.api.post<void>('users', registerData).subscribe
        (
            data => {
                this.goHome();
            },
            error => {
                alert('Het registreren is mislukt');
            }
        );
    }

    public login(user: User, remember: boolean): void {
        this.authService.setAuthorization(user.email, user.password);

        this.api.get<User>('users/me').subscribe
        (
            authenticator => {
                this.authService.storeAuthorization(authenticator, remember);

                this.goHome();
            },
            error => {
                alert('Het inloggen is mislukt');
            }
        );
    }

    public logout() {
        this.authService.deleteAuthorization();

        this.goHome();
    }

  private goHome() {
    this.router.navigate(['home']);
  }
}
