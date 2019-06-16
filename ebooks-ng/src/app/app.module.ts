import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER} from '@angular/core';
import { ReaderModule } from './reader/reader.module';
import { RouterModule } from '@angular/router';
import { SecurityModule } from './security/security.module';
import { KeycloakService } from 'keycloak-angular';

import { AppComponent } from './app.component';
import { EbooksListComponent } from './reader/ebooksList.component';
import { AppAuthGuard } from './security/appAuth.guard';
import { environment } from '../environments/environment';

const routes = RouterModule.forRoot([
  { path: 'ebooks', component: EbooksListComponent },
  { path: 'admin', loadChildren: './admin/admin.module#AdminModule', canActivate: [AppAuthGuard]},
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
  providers: [{
    provide: APP_INITIALIZER,
    useFactory: initKeycloak,
      multi: true,
      deps: [KeycloakService]
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }

function initKeycloak(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => keycloak.init(
        {
          config: environment.keycloak,
          initOptions: {
            onLoad: 'check-sso',
            checkLoginIframe: false
          },
          bearerExcludedUrls: []
        }
    );
}
