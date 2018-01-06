import { Component, OnInit, Input } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { AuthorizationService } from '../../shared/authorization.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User;

  constructor(private userService: UserService, private authService: AuthorizationService, private router: Router) {
    this.user = new User();
  }

  ngOnInit() {
  }

  public createUser(user: User) {
    console.log('CREATE CLIENT', user);
    this.userService.create(user).subscribe(
      data => {
        user = data;
        console.log('Client insert:', user);
        this.login();
      },
      error => {
        console.log('ERORR');
      }
    );
  }

  login() {
    this.userService.login(this.user).subscribe(
      data => {
        this.userService.storeAuth(data, false);
        console.log('GELUKT', this.authService.getAuthenticator());
        this.userService.goHome();
      },
      error => {
        console.log('probleem');
      }
    );
  }

}
