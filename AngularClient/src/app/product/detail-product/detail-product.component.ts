import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { AuthorizationService } from '../../shared/authorization.service';

@Component({
  selector: 'app-detail-product',
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.css']
})
export class DetailProductComponent implements OnInit {
  product: Product;
  public dataSource = null;

  constructor(private router: Router, private route: ActivatedRoute, private productService: ProductService,
      private authService: AuthorizationService) {
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
      },
      error => {
        this.router.navigate(['product/' + id + '/NotFound']);
      });
  }

}
