import {Category} from './category';

export class Product {
  constructor(
    public id: number = null,
    public name?: string,
    public description?: string,
    public category: Category = new Category(),
    public price?: number,
    public image?: String
  ) {

  }
}
