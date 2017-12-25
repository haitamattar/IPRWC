import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsComponent } from './products/products.component';
import { CreateProductComponent } from './create-product/create-product.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [ProductsComponent, CreateProductComponent]
})
export class ProductModule { }
