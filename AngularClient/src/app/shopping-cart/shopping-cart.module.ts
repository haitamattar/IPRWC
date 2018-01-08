import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { ShoppingCartOverviewComponent } from './shopping-cart-overview/shopping-cart-overview.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [ShoppingCartOverviewComponent]
})
export class ShoppingCartModule { }
