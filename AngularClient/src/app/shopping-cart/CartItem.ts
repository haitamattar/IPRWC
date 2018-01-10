import { Product } from '../product/product';
import { User } from '../user/user';

export class CartItem {
  constructor(
    public product: Product = new Product(),
    public total: number = 1
  ) {

  }

}
