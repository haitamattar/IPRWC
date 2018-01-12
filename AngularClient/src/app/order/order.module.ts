import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderDetailComponent } from './order-detail/order-detail.component';
import { SharedModule } from '../shared/shared.module';
import { OrderService } from './order.service';

@NgModule({
  imports: [
    CommonModule,
    SharedModule
  ],
  declarations: [OrderDetailComponent],
  providers: [OrderService]
})
export class OrderModule { }
