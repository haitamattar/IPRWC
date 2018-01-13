import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { PublicModule } from '../public.module';

@NgModule({
  imports: [
    CommonModule,
    PublicModule
  ],
  declarations: [HomeComponent]
})
export class HomeModule { }
