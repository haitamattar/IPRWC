import { Product } from '../product/product';
import { User } from '../user/user';
import { CartItem } from './CartItem';

export class ShoppingCart {
  constructor(
    public user: User = new User(),
    public cartItems: CartItem[] = []
  ) {

  }

  public getAllProducts(): CartItem[] {
    return this.cartItems;
  }

  public addProduct(cartItem: CartItem) {
    this.cartItems.push(cartItem);
  }

  setUser(user: User) {
    this.user = user;
  }

  setAllProducts(cartItem: CartItem[]) {
    this.cartItems = cartItem;
  }


  getAmountOfCartItems(): number {
    let num = 0;
    for (const cartItem of this.cartItems) {
      num += cartItem.total;
    }
    return num;
  }

  getTotalCost(): number {
    let num = 0;
    for (const cartItem of this.cartItems) {
      num += cartItem.product.price * cartItem.total;
    }
    return num;
  }
}
