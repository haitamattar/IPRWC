import { Injectable } from '@angular/core';
import { CartItem } from './CartItem';
import { User } from '../user/user';
import { BehaviorSubject, Observable, Subject, Subscriber } from 'rxjs/';
import { ShoppingCart } from './shopping-cart';
import { Product } from '../product/product';

import { AuthorizationService } from '../shared/authorization.service';
import { of } from 'rxjs/observable/of';
import { ApiService } from '../shared/api.service';


@Injectable()
export class ShoppingCartService {

  private itemsInCartSubject: BehaviorSubject<ShoppingCart> = new BehaviorSubject(new ShoppingCart);
  private shoppingCart: ShoppingCart = new ShoppingCart();
  private currentUser: User = new User();

  constructor(private api: ApiService, private authService: AuthorizationService) {
    this.itemsInCartSubject.subscribe(_ => this.shoppingCart = _);
    this.restoreShoppingCartSession();
  }


  public addToCart(item: Product) {
    const cartItem = new CartItem(item, 1);
    // this.getAmountOfCartItems().subscribe(data => console.log('doelans', data));
    for (let i = 0; i < this.shoppingCart.getAllProducts().length; i++) {

      if (this.shoppingCart.cartItems[i].product.id === cartItem.product.id) {
        this.shoppingCart.cartItems[i].total += 1;
        this.itemsInCartSubject.next(this.shoppingCart);
        this.setShoppingCartSession(this.shoppingCart);
        if (this.currentUser != null) {
          this.updateShoppingCartInDatabase(this.shoppingCart).subscribe(_ => _);
        }
        return;
      }

    }

    this.shoppingCart.addProduct(cartItem);
    this.itemsInCartSubject.next(this.shoppingCart);
    this.setShoppingCartSession(this.shoppingCart);
    if (this.currentUser != null) {
      this.postProductInDatabase(cartItem).subscribe(_ => _);
    }
    return;
  }


  public getItems(): Observable<CartItem[]> {
    const observableList: BehaviorSubject<CartItem[]> = new BehaviorSubject([]);
    observableList.next(this.shoppingCart.getAllProducts());
    return observableList;
  }


  public setShoppingCart(shoppingCart: ShoppingCart) {
    this.shoppingCart = shoppingCart;
    this.itemsInCartSubject.next(this.shoppingCart);
    this.setShoppingCartSession(this.shoppingCart);
  }


  public getShoppingCart(): ShoppingCart {
    return this.shoppingCart;
  }


  public getShoppingCartOb(): Observable<ShoppingCart> {
    return this.itemsInCartSubject;
  }


  public removeCurrentUser() {
      this.currentUser = null;
      this.shoppingCart.setUser(this.currentUser);
  }


  public retrieveAllDataFromDatabase() {
    this.currentUser = this.authService.getAuthenticator();
    this.shoppingCart.setUser(this.currentUser);

    this.getShoppingCartObjectFromDatabase()
      .subscribe(
      data => {
        this.shoppingCart = new ShoppingCart(data.user, data.cartItems);
        this.itemsInCartSubject.next(this.shoppingCart);
        this.setShoppingCartSession(this.shoppingCart);
      },
      error => {
        console.log('No data in shoppingCart');
      });
  }


  // Set Session cart data
  public setShoppingCartSession(shoppingCartItems: ShoppingCart) {
    this.shoppingCart = shoppingCartItems;
    const shoppingCartString = JSON.stringify(shoppingCartItems);
    const storage = localStorage;
    storage.setItem('shoppingCart', shoppingCartString);
  }


  // get Session cart data
  private restoreShoppingCartSession(): void {
    this.currentUser = this.authService.getAuthenticator();
    let shoppingCartString = localStorage.getItem('shoppingCart');

    if (shoppingCartString !== null) {
      const shoppingCartParseString = JSON.parse(shoppingCartString);
      this.itemsInCartSubject.next(shoppingCartParseString);
      this.shoppingCart = new ShoppingCart(shoppingCartParseString['user'], shoppingCartParseString['cartItems']);
    }
  }


  public removeShoppingCartSession(): void {
    this.shoppingCart = new ShoppingCart();
    this.itemsInCartSubject.next(this.shoppingCart);
    localStorage.removeItem('shoppingCart');
  }

  // Database related functions
  public getShoppingCartObjectFromDatabase(): Observable<ShoppingCart> {
    return this.api.get<ShoppingCart>('shoppingCart', this.currentUser);
  }

  public postProductInDatabase(cartItem: CartItem): Observable<CartItem> {
    return this.api.post<CartItem>('shoppingCart', cartItem);
  }

  public updateShoppingCartInDatabase(shoppingCart: ShoppingCart): Observable<ShoppingCart> {
    console.log('TESTOE A NEEF');
    return this.api.put<ShoppingCart>('shoppingCart', shoppingCart);
  }

  public purchaseItems(shoppingCart: ShoppingCart): Observable<ShoppingCart> {
      this.removeShoppingCartSession();
      return this.api.post<ShoppingCart>('orders', shoppingCart);
  }

}
