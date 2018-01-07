import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { PublicModule } from '../../public.module';
import { AuthorizationService } from '../authorization.service';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';
import { Product } from '../../product/product';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public shoppingCartItems$: Observable<Product[]>;


  private isLoggedIn$: Observable<boolean>;

  constructor(private authService: AuthorizationService, private shoppingCartService: ShoppingCartService,
    private router: Router) {

  }

  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn;
    console.log('AUTH', this.isLoggedIn$);
    this.shoppingCartItems$ = this.shoppingCartService.getItems();
    this.shoppingCartItems$.subscribe(_ => _);
  }

  public logout() {
    this.authService.deleteAuthorization();
    this.shoppingCartService.removeShoppingCartSession();
    this.shoppingCartItems$ = null;
    this.shoppingCartItems$.subscribe(_ => _);
    this.router.navigate(['login']);
  }

}
