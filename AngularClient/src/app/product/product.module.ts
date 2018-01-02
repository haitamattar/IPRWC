import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { ProductService } from './product.service';

import { ProductsComponent } from './products/products.component';
import { CreateProductComponent } from './create-product/create-product.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule
  ],
  declarations: [ProductsComponent, CreateProductComponent],
  providers: [ProductService]
})
export class ProductModule { }
