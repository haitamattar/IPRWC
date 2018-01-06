import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  public dataSource = null;

  constructor(private productService: ProductService) {
    this.getAllProducts();
  }

  private getAllProducts() {
    this.productService.getAll().subscribe(
      products => {
        this.dataSource = products;
        this.dataSource = this.chunck(this.dataSource, 3);
        console.log(this.dataSource);
    },
    error => {
        console.log('Probleem');
    }
    );
  }

  chunck(array, size) {
    const results = [];
    while (array.length) {
      results.push(array.splice(0, size));
    }
    return results;
  }

  ngOnInit() {
  }

}
