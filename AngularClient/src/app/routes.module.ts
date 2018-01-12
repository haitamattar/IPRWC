import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { RoleGuardService } from './shared/role-guard.service';
// User
import { LoginComponent } from './user/login/login.component';
import { UserDetailComponent } from './user/user-detail/user-detail.component';
import { RegisterComponent } from './user/register/register.component';

import { HomeComponent } from './home/home.component';
// Products
import { ProductsComponent } from './product/products/products.component';
import { CreateProductComponent } from './product/create-product/create-product.component';
import { DetailProductComponent } from './product/detail-product/detail-product.component';
// ShoppingCart
import { ShoppingCartOverviewComponent } from './shopping-cart/shopping-cart-overview/shopping-cart-overview.component';
import { OrderDetailComponent } from './order/order-detail/order-detail.component';

import { AuthGuard } from './shared/auth-guard.service';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';

export const routes: Routes =
[
    {
        path: 'admin',
        canActivate: [AuthGuard, RoleGuardService],
        data: { allowedRole: 'ADMIN' },
        children: [
            {
                        path: 'maakProduct',
                        component: CreateProductComponent
                    },
        ]
    },
    {
        path: 'user',
        canActivate: [AuthGuard, RoleGuardService],
        data: { allowedRole: 'CUSTOMER' },
        children: [
            { path: 'me', component: UserDetailComponent },
            { path: 'order/:id', component: OrderDetailComponent }
        ]
    },
    // public routes
    { path: '', component: HomeComponent },
    { path: 'products', component: ProductsComponent},
    { path: 'product/:id', component: DetailProductComponent},
    { path: 'shoppingCart', component: ShoppingCartOverviewComponent},
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class RoutesModule { }
