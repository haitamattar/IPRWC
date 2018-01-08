import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { AuthorizationService } from '../../shared/authorization.service';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();


  constructor(private userService: UserService, private shoppingCartService: ShoppingCartService,
       private authService: AuthorizationService, private router: Router) { }

  ngOnInit() {
  }

  login() {this.userService.login(this.user).subscribe(
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
