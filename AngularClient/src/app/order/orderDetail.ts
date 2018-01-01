import {Product} from '../product/product';

export class OrderDetail {
  constructor(
    public id: number = null,
    public product?: Product,
    public productPrice?: number // history price
  ) {

  }
}
