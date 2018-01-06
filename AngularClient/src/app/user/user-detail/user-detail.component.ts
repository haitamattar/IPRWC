import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { AuthorizationService } from '../../shared/authorization.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  user: User;
  public dataSource = null;

  constructor(private userService: UserService, private authService: AuthorizationService) {
    this.user = new User();
  }

  ngOnInit() {
      this.getUser();
  }

  getUser(): void {
      this.userService.getDetail(this.authService.getAuthenticator())
       .subscribe(user => this.user = user);
  }

  createUser() {
      console.log('create user');
  }

}
