import {Product} from '../product/product';
import {User} from '../user/user';

export class ShoppingCart {
  constructor(
    public user: User = new User(),
    public products: Product[]
  ) {

  }
}
