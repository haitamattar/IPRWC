import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { AuthorizationService } from '../../shared/authorization.service';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  user: User;

  constructor(private userService: UserService, private authService: AuthorizationService,
    private shoppingCartService: ShoppingCartService) {
    this.user = new User();
  }

  ngOnInit() {
    this.getUser();
  }

  getUser(): void {
    this.userService.getDetail(this.authService.getAuthenticator())
      .subscribe(user => this.user = user);
  }

  editUser(user: User) {
    console.log('edit user');
    this.userService.update(this.user).subscribe(
      data => {
        user = data;
        this.loginWithNewData();
      },
      error => {
        console.log('ERROR');
        // this.showSnackbar('Er is een fout opgetreden bij het toevoegen', '#b10617');
      }
    );
  }

  loginWithNewData() {
    this.userService.login(this.user).subscribe(
      data => {
        this.userService.storeAuth(data, false);
        this.shoppingCartService.retrieveAllDataFromDatabase();
      },
      error => {
        console.log('Error');
      }
    );

  }
}
