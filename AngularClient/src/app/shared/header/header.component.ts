import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { PublicModule } from '../../public.module';
import { AuthorizationService } from '../authorization.service';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';
import { ShoppingCart } from '../../shopping-cart/shopping-cart';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public shoppingCartItems$: Observable<ShoppingCart>;
  public amountCart: number;
  public authenticatorRole: string;

  public isLoggedIn$: Observable<boolean>;

  constructor(private authService: AuthorizationService, private shoppingCartService: ShoppingCartService,
    private router: Router) {
  }

  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn;
    this.shoppingCartItems$ = this.shoppingCartService.getShoppingCartOb();
    this.shoppingCartItems$.subscribe(_ => _);
    this.shoppingCartItems$.subscribe(
      data => {
        this.amountCart = new ShoppingCart(data.user, data.cartItems).getAmountOfCartItems();
      },
      _ => { this.amountCart = 0; });

      if (this.authService.hasAuthorization()) {
          this.authenticatorRole = this.authService.getAuthenticator().role;
      }

      this.authService.authenticator$.subscribe(
            authenticator => {
                this.authenticatorRole = authenticator.role;
            }
        );
  }

  public logout() {
    this.shoppingCartService.removeShoppingCartSession();
    this.authenticatorRole = '';
    this.authService.deleteAuthorization();
    this.shoppingCartItems$.subscribe(_ => _);
    this.shoppingCartService.removeCurrentUser();
    this.router.navigate(['login']);
  }

}
