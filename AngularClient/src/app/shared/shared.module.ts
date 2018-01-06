import { NgModule } from '@angular/core';
import {PublicModule} from '../public.module';
import { AuthorizationService } from './authorization.service';
import { ApiService } from './api.service';

import { HeaderComponent } from './header/header.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

@NgModule({
  imports: [
    PublicModule
  ],
  declarations: [HeaderComponent, PageNotFoundComponent],
  exports: [ HeaderComponent ],
  providers: [ ApiService, AuthorizationService]
})
export class SharedModule { }
