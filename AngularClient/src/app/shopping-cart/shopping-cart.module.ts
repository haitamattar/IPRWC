import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { ShoppingCartOverviewComponent } from './shopping-cart-overview/shopping-cart-overview.component';
import { FormsModule } from '@angular/forms';
import { PublicModule } from '../public.module';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    PublicModule
  ],
  declarations: [ShoppingCartOverviewComponent]
})
export class ShoppingCartModule { }
