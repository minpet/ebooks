import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReaderModule } from './reader/reader.module';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { EbooksListComponent } from './reader/ebooksList.component';

const routes = RouterModule.forRoot([
  { path: 'ebooks', component: EbooksListComponent },
  { path: '**', redirectTo: '/ebooks'}
]);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    ReaderModule,
    routes
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
