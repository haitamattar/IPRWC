import { Component, OnInit } from '@angular/core';
import { OrderService } from './../order.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Order } from './../order';
@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {

  order: Order;

  constructor(private orderService: OrderService, private route: ActivatedRoute) {
    this.order = new Order();
    this.getOrder();
  }

  ngOnInit() {

  }

  getOrder(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.orderService.getSingle(id)
      .subscribe(
      order => {
        this.order = order;
        console.log('this Order: ', this.order);

      },
      error => { console.log('PRONNNE'); }
      );

  }

}
