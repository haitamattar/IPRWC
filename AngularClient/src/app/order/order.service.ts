import { Injectable } from '@angular/core';
import { Order } from './order';
import { Observable } from 'rxjs/Observable';
import { ApiService } from '../shared/api.service';

@Injectable()
export class OrderService {

  constructor(private api: ApiService) { }

  // Get all by id
  public getSingle(id: number): Observable<Order> {
    return this.api.get<Order>('orders/' + id);
  }

}
