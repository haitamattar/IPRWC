import { Injectable } from '@angular/core';
import { Product } from '../product/product';
import { User } from '../user/user';
import { BehaviorSubject, Observable, Subject, Subscriber } from 'rxjs/';
import { ShoppingCart } from './shopping-cart';
import { AuthorizationService } from '../shared/authorization.service';
import { of } from 'rxjs/observable/of';
import { ApiService } from '../shared/api.service';


@Injectable()
export class ShoppingCartService {

  private itemsInCartSubject: BehaviorSubject<Product[]> = new BehaviorSubject([]);
  private itemsInCart: Product[] = [];
  private shoppingCart: ShoppingCart = new ShoppingCart();
  private currentUser: User = new User();

  constructor(private api: ApiService, private authService: AuthorizationService) {
    this.itemsInCartSubject.subscribe(_ => this.itemsInCart = _);
    this.restoreShoppingCartSession();
  }



  public addToCart(item: Product) {
    console.log('Add to cart');
    console.log('CART ALL PSOR: ', this.authService.getAuthenticator());


    this.itemsInCartSubject.next([...this.itemsInCart, item]);
    this.shoppingCart.products = this.itemsInCart;
    console.log('SHIT: ', this.shoppingCart.getAllProducts());
    this.setShoppingCartSession(this.shoppingCart);
  }

  public getItems(): Observable<Product[]> {
    return this.itemsInCartSubject;
  }

  public retrieveAllDataFromDatabase() {
    this.currentUser = this.authService.getAuthenticator();
    this.shoppingCart.setUser(this.currentUser);

    this.getShoppingCartObjectFromDatabase()
      .subscribe(
      data => {
        console.log('Length cart: ', data.products.length);
        // if (data.products.length > 0) {
          console.log('USER SHOP: ', data.user);
          this.shoppingCart = new ShoppingCart(data.user, data.products);
          console.log('DATABSE SHOPINGCART data: ', this.shoppingCart);
          this.itemsInCart = this.shoppingCart.getAllProducts();
          this.itemsInCartSubject.next(this.shoppingCart.getAllProducts());
          this.setShoppingCartSession(this.shoppingCart);
        // }
      },
      error => {
        console.log('Cannot get data form shoppingCart');
      });
  }

  public getShoppingCartObjectFromDatabase(): Observable<ShoppingCart> {
    console.log('user: ', this.currentUser);
    return this.api.get<ShoppingCart>('shoppingCart', this.currentUser);
  }


  // Get Session cart data
  public setShoppingCartSession(shoppingCartItems: ShoppingCart) {
    this.shoppingCart.setAllProducts(shoppingCartItems.getAllProducts());
    const shoppingCartString = JSON.stringify(shoppingCartItems);
    const storage = sessionStorage;

    storage.setItem('shoppingCart', shoppingCartString);
  }

  private restoreShoppingCartSession(): void {
    this.currentUser = this.authService.getAuthenticator();

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
