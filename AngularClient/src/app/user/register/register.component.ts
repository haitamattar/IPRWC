import { Component, OnInit, Input } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { AuthorizationService } from '../../shared/authorization.service';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User;

  constructor(private userService: UserService, private authService: AuthorizationService, private router: Router,
  private shoppingCartService: ShoppingCartService) {
    this.user = new User();
  }

  ngOnInit() {
  }

  public createUser(user: User) {
    this.userService.create(user).subscribe(
      data => {
        user = data;
        this.login();
      },
      error => {
        console.log('Error');
      }
    );
  }

  login() {
    this.userService.login(this.user).subscribe(
      data => {
        this.userService.storeAuth(data, false);
        this.shoppingCartService.retrieveAllDataFromDatabase();
        this.userService.goHome();
      },
      error => {
        console.log('Error');
      }
    );
  }

}
