import {Product} from '../product/product';
import {User} from '../user/user';

export class ShoppingCart {
  constructor(
    public user: User = new User(),
    public products: Product[] = []
  ) {

  }

  public getAllProducts() {
      return this.products;
  }

  public addProduct(product: Product) {
      this.products.push(product);
  }

  setUser(user: User) {
      this.user = user;
  }

  setAllProducts(products: Product[]) {
      this.products = products;
  }
}
