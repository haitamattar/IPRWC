import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { AuthorizationService } from '../../shared/authorization.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();


  constructor(private userService: UserService, private authService: AuthorizationService, private router: Router) { }

  ngOnInit() {
  }

  login() {this.userService.login(this.user).subscribe(
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
