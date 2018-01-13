import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { PublicModule } from '../public.module';
import { UserService } from './user.service';
import { RegisterComponent } from './register/register.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UserOverviewComponent } from './user-overview/user-overview.component';


@NgModule({
  imports: [
    PublicModule,
    CommonModule
  ],
  exports: [ LoginComponent ],
  declarations: [LoginComponent, RegisterComponent, UserDetailComponent, UserOverviewComponent],
  providers: [ UserService ]
})
export class UserModule { }
