import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  public products: Product[] = [];

  constructor(private productService: ProductService) {
    this.getAllProducts();
  }

  private getAllProducts() {
    this.productService.getAll().subscribe(
      products => {
        this.products = products;
        this.products = this.chunck(this.products, 3);
        console.log(this.products);
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
