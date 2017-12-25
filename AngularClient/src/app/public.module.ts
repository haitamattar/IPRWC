import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
      CommonModule,
      RouterModule,
      FormsModule,
      HttpClientModule,
  ],
  exports: [
      CommonModule,
      RouterModule,
      FormsModule,
      HttpClientModule
  ],
  declarations: []
})
export class PublicModule { }
