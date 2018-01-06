import { Injectable } from '@angular/core';
import { ApiService } from '../shared/api.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { AuthorizationService } from '../shared/authorization.service';
import { Product } from './product';


@Injectable()
export class ProductService {

  constructor(private api: ApiService, private authService: AuthorizationService, private router: Router) {

  }

  // Get all clients
  public getAll(): Observable<Product[]> {
    return this.api.get<Product[]>('products');
  }

  public getDetail(id: Number): Observable<Product> {
    return this.api.get<Product>('products/' + id);
  }

}
