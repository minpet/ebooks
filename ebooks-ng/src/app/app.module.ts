import { BrowserModule } from '@angular/platform-browser';
import { NgModule} from '@angular/core';
import { ReaderModule } from './reader/reader.module';
import { RouterModule } from '@angular/router';
import { SecurityModule } from './security/security.module';

import { AppComponent } from './app.component';
import { EbooksListComponent } from './reader/ebooksList.component';
import { AppAuthGuard } from './security/appAuth.guard';

const routes = RouterModule.forRoot([
  { path: 'ebooks', component: EbooksListComponent },
  { path: 'admin', loadChildren: './admin/admin.module#AdminModule', canActivate: [AppAuthGuard], data: {roles: ['authenticated']}},
  { path: '**', redirectTo: '/ebooks'}
]);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    SecurityModule,
    BrowserModule,
    ReaderModule,
    routes
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
