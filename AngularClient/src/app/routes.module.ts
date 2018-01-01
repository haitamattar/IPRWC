import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { RoleGuardService } from './shared/role-guard.service';

import { LoginComponent } from './user/login/login.component';
import { HomeComponent } from './home/home.component';

import { ProductsComponent } from './product/products/products.component';
import { CreateProductComponent } from './product/create-product/create-product.component';

import { AuthGuard } from './shared/auth-guard.service';

export const routes: Routes =
[
    {
        path: 'admin',
        canActivate: [AuthGuard, RoleGuardService],
        data: { allowedRole: 'ADMIN' },
        children: [
            { path: 'products', component: ProductsComponent},
            {
                        path: 'maakProduct',
                        component: CreateProductComponent
                    },
        ]
    },

    { path: '', component: HomeComponent },

    { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class RoutesModule { }
