import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { RoleGuardService } from './shared/role-guard.service';

import { LoginComponent } from './user/login/login.component';
import { ProductsComponent } from './product/products/products.component';
import { CreateProductComponent } from './product/create-product/create-product.component';

import { AuthGuard } from './shared/auth-guard.service';

export const routes: Routes =
[
    {
        path: '',
        canActivate: [AuthGuard],
        children: [
            { path: 'products', component: ProductsComponent}
        ]
    },
    {
                path: 'maakProduct',
                canActivate: [RoleGuardService],
                data: { allowedRole: 'ADMIN' },
                component: CreateProductComponent
            },

    { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class RoutesModule { }
