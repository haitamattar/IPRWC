import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { PublicModule } from './public.module';

import { AppComponent } from './app.component';

import {AuthGuard} from './shared/auth-guard.service';
import {UserModule} from './user/user.module';

import {SharedModule} from './shared/shared.module';

import { RoleGuardService } from './shared/role-guard.service';
import { RoutesModule } from './routes.module';
import { ProductModule } from './product/product.module';
import { HomeModule } from './home/home.module';



@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    UserModule,
    SharedModule,
    RoutesModule,
    ProductModule,
    HomeModule
  ],
  exports: [
    PublicModule
  ],
  providers: [AuthGuard, RoleGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
