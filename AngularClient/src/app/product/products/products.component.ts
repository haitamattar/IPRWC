import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../product';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  public products: Product[] = [];
  public allProducts: Product[] = [];
  public category: string;

  constructor(private productService: ProductService, private route: ActivatedRoute,
    private shopService: ShoppingCartService) {
    this.route.params.subscribe(
      (params) => {
        this.category = params['category'];
        this.getAllProducts();
      });
  }

  private getAllProducts() {
    this.productService.getAll().subscribe(
      products => {
        this.allProducts = products;
        this.products = this.allProducts;
        if (this.category != null) {
          this.products = this.allProducts.filter((product: Product) => product.category.name === this.category);
        }

        this.products = this.chunck(this.products, 2);
      },
      error => {
        console.log('Error');
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

  addToShoppingCart(product: Product) {
    this.shopService.addToCart(product);
  }

  ngOnInit() {

  }

}
