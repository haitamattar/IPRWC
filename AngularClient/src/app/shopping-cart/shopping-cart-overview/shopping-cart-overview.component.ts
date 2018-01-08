import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../../shopping-cart/shopping-cart.service';
import { ShoppingCart } from '../../shopping-cart/shopping-cart';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import * as _ from 'lodash';

@Component({
  selector: 'app-shopping-cart-overview',
  templateUrl: './shopping-cart-overview.component.html',
  styleUrls: ['./shopping-cart-overview.component.css']
})
export class ShoppingCartOverviewComponent implements OnInit {
  shoppingCart: ShoppingCart = new ShoppingCart();
  result: any = [];
  dataSource;
  uniqueData$;


  constructor(private shoppingcartService: ShoppingCartService) { }

  ngOnInit() {
    this.shoppingCart = this.shoppingcartService.getWholeShoppingCart();
    this.dataSource = Observable.of(this.shoppingCart.getAllProducts());
    this.uniqueData$ = this.dataSource.map(data => _.uniqBy(data, 'name'));
    console.log("HOOOOIL: ", this.dataSource);
    console.log('DATASOURCE', this.uniqueData$);
  }



}
