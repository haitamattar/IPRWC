import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../product';
import { ProductService } from '../product.service';


@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {

    product: Product = new Product();

  constructor(private productService: ProductService, private route: Router) { }

  ngOnInit() {
  }

  createProduct(product: Product) {
      product.image = '/assets/images/nintendo64.png';
      this.productService.create(product).subscribe(
      data => {
        this.product = data;
        this.route.navigate(['product/' + this.product.id]);
      },
      error => {
        console.log('Error');
      }
    );
  }

}
