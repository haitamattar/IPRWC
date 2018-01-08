import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShoppingCartOverviewComponent } from './shopping-cart-overview.component';

describe('ShoppingCartOverviewComponent', () => {
  let component: ShoppingCartOverviewComponent;
  let fixture: ComponentFixture<ShoppingCartOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShoppingCartOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShoppingCartOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
