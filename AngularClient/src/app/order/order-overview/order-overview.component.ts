import { Component, OnInit } from '@angular/core';
import { OrderService } from './../order.service';
import { ActivatedRoute } from '@angular/router';
import { Order } from './../order';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { User } from '../../user/user';

@Component({
  selector: 'app-order-overview',
  templateUrl: './order-overview.component.html',
  styleUrls: ['./order-overview.component.css']
})
export class OrderOverviewComponent implements OnInit {

  public orders: Order[] = [];

  isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  constructor(private orderService: OrderService) {
  }

  ngOnInit() {
    this.getAllOrderByUser();
    this.isLoading$.next(true);

  }

  getAllOrderByUser() {
    this.orderService.getAllByUser().subscribe(
      orders => {
        // Cast data to object
        for (const order of orders) {
            console.log(order);
          this.orders.push(new Order(order.id, order.orderDateTime, order.user, order.ordersDetail));
        }
      },
      error => {
        console.log('Error');
      },
      () => {
        this.isLoading$.next(false);
      }
    );
  }

}
