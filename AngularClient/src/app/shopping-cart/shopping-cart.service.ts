import { Injectable } from '@angular/core';
import { Product } from '../product/product';
import { User } from '../user/user';
import { BehaviorSubject, Observable, Subject, Subscriber } from 'rxjs/';
import { ShoppingCart } from './shopping-cart';
import { AuthorizationService } from '../shared/authorization.service';
import {of} from 'rxjs/observable/of';


@Injectable()
export class ShoppingCartService {

    private itemsInCartSubject: BehaviorSubject<Product[]> = new BehaviorSubject([]);
    private itemsInCart: Product[] = [];
    shoppingCart: ShoppingCart = new ShoppingCart();

  constructor(authService: AuthorizationService) {
    this.itemsInCartSubject.subscribe(_ => this.itemsInCart = _);
    this.shoppingCart.setUser(authService.getAuthenticator());
    this.restoreShoppingCartSession();
  }



  public addToCart(item: Product) {
    this.itemsInCartSubject.next([...this.itemsInCart, item]);
    this.setShoppingCartSession(this.itemsInCart);
  }

  public getItems(): Observable<Product[]> {
    return this.itemsInCartSubject;
  }

  public setShoppingCartSession(shoppingCartItems: Product[]) {
      this.shoppingCart.setAllProducts(shoppingCartItems);
      const shoppingCartString = JSON.stringify(this.shoppingCart);
      const storage = sessionStorage;

      storage.setItem('shoppingCart', shoppingCartString);
  }

  private restoreShoppingCartSession(): void {
    let shoppingCartString = sessionStorage.getItem('shoppingCart');

    if (shoppingCartString === null) {
      shoppingCartString = localStorage.getItem('shoppingCart');
    }

    if (shoppingCartString !== null) {
      const shoppingCartParseString = JSON.parse(shoppingCartString);

      this.itemsInCart = shoppingCartParseString['products'];
      this.itemsInCartSubject.next(shoppingCartParseString['products']);

      this.shoppingCart.setAllProducts(shoppingCartParseString['products']);
      console.log('shoppingcart = ', this.shoppingCart);
    }
  }

  public removeShoppingCartSession(): void {
      this.itemsInCart = null;
      this.itemsInCartSubject.next(null);
      this.shoppingCart = null;
      sessionStorage.removeItem('shoppingCart');
  }

}
