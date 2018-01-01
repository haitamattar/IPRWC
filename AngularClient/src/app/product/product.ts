import {Category} from './category';

export class Product {
  constructor(
    public id: number = null,
    public name?: string,
    public description?: string,
    public category?: Category,
    public price?: number
  ) {

  }
}
