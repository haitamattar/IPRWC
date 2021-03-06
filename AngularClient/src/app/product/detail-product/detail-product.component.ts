import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { AuthorizationService } from '../../shared/authorization.service';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';

@Component({
  selector: 'app-detail-product',
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.css']
})
export class DetailProductComponent implements OnInit {
  public product: Product = null;
  public isDataAvailable: Boolean = false;

  constructor(private router: Router, private route: ActivatedRoute, private productService: ProductService,
    private authService: AuthorizationService, private shoppingCartService: ShoppingCartService) {
    this.product = new Product();
  }

  ngOnInit() {
    this.getProduct();
  }

  getProduct(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.getDetail(id)
      .subscribe(
      product => {
        this.product = product;
        this.isDataAvailable = true;
      },
      error => {
        this.router.navigate(['product/' + id + '/NotFound']);
      });
  }

  addToShoppingCart() {
    this.shoppingCartService.addToCart(this.product);
  }

}
