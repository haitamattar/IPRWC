import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { PublicModule } from '../public.module';
import { UserService } from './user.service';


@NgModule({
  imports: [
    PublicModule,
    CommonModule
  ],
  exports: [ LoginComponent ],
  declarations: [LoginComponent],
  providers: [ UserService ]
})
export class UserModule { }
