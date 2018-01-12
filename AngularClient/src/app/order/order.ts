import {User} from '../user/user';
import {OrderDetail} from './orderDetail';


export class Order {
  constructor(
    public id: number = null,
    public orderDateTime?: Date,
    public user: User = new User(),
    public ordersDetail: OrderDetail[] = []
  ) {

  }
}
