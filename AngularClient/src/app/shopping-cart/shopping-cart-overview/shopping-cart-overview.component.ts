import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';
import { ShoppingCart } from '../../shopping-cart/shopping-cart';
import { Observable } from 'rxjs/Observable';
import { AuthorizationService } from '../../shared/authorization.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-shopping-cart-overview',
  templateUrl: './shopping-cart-overview.component.html',
  styleUrls: ['./shopping-cart-overview.component.css']
})
export class ShoppingCartOverviewComponent implements OnInit, OnDestroy {
  shoppingCart: ShoppingCart = new ShoppingCart();
  result: any = [];
  dataSource;
  uniqueData$;


  constructor(private shoppingCartService: ShoppingCartService, private authService: AuthorizationService,
    private router: Router) { }

  ngOnInit() {
    this.shoppingCart = this.shoppingCartService.getShoppingCart();
  }

  ngOnDestroy() {
    if (this.authService.hasAuthorization() === true) {
      this.updateCartInDatabase();
    }
  }

  updateCartInDatabase() {
    this.shoppingCartService.updateShoppingCartInDatabase(this.shoppingCart).subscribe(_ => _);
  }

  editCart() {
    this.shoppingCartService.setShoppingCart(this.shoppingCart);
  }

  purchase() {
    if (this.authService.getAuthenticator() == null ) {
        this.router.navigate(['login']);
    }

    this.shoppingCartService.purchaseItems(this.shoppingCart).subscribe(
      data => {
        this.router.navigate(['order', data]);
        this.shoppingCartService.removeShoppingCartSession();
      },
      error => {
        console.log('error');
      });
  }



}
