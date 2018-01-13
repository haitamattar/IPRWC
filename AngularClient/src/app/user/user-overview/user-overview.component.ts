import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-user-overview',
  templateUrl: './user-overview.component.html',
  styleUrls: ['./user-overview.component.css']
})
export class UserOverviewComponent implements OnInit {

  // public shoppingCartItems$: Observable<ShoppingCart>;
  users: User[] = [];

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.getAll().subscribe(users => this.users = users);
  }

  removeUser(user: User) {
    this.userService.delete(user).subscribe(
      users => {
          this.getAllUsers();
      }
    );
  }


}
