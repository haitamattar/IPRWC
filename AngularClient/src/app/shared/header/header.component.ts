import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PublicModule } from '../../public.module';
import { AuthorizationService } from '../authorization.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public authenticated: boolean;

  constructor(private authService: AuthorizationService) {

  }

  ngOnInit() {
    this.authenticated = this.authService.hasAuthorization();
  }


}
