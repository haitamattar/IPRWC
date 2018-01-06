import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { PublicModule } from '../../public.module';
import { AuthorizationService } from '../authorization.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn$: Observable<boolean>;

  constructor(private authService: AuthorizationService, private router: Router) {

  }

  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn;
    console.log('AUTH', this.isLoggedIn$);
  }

  public logout() {
      this.authService.deleteAuthorization();
      this.router.navigate(['login']);
  }

}
