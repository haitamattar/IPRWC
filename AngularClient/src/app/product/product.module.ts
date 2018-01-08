import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { ProductService } from './product.service';
import { ShoppingCartService } from '../shopping-cart/shopping-cart.service';
import { RouterModule } from '@angular/router';
import { ProductsComponent } from './products/products.component';
import { CreateProductComponent } from './create-product/create-product.component';
import { DetailProductComponent } from './detail-product/detail-product.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    RouterModule
  ],
  declarations: [ProductsComponent, CreateProductComponent, DetailProductComponent],
  providers: [ProductService, ShoppingCartService]
})
export class ProductModule { }
