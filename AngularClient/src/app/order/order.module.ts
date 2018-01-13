import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderDetailComponent } from './order-detail/order-detail.component';
import { SharedModule } from '../shared/shared.module';
import { OrderService } from './order.service';
import { OrderOverviewComponent } from './order-overview/order-overview.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    RouterModule
  ],
  declarations: [OrderDetailComponent, OrderOverviewComponent],
  providers: [OrderService]
})
export class OrderModule { }
