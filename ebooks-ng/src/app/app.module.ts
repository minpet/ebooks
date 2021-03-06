import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER} from '@angular/core';
import { ReaderModule } from './reader/reader.module';
import { RouterModule } from '@angular/router';
import { SecurityModule } from './security/security.module';
import { CommonModule } from '@angular/common';
import { KeycloakService } from 'keycloak-angular';

import { AppComponent } from './app.component';
import { LogoutComponent } from './logout.component';
import { EbooksListComponent } from './reader/ebooksList.component';
import { ReaderComponent } from './reader/reader.component';
import { AppAuthGuard } from './security/appAuth.guard';
import { environment } from '../environments/environment';
import { VersionDataSource } from './version/version.datasource';

const routes = RouterModule.forRoot([
  { path: 'ebooks/:id', component: ReaderComponent },
  { path: 'ebooks', component: EbooksListComponent },
  { path: 'admin', loadChildren: './admin/admin.module#AdminModule', canActivate: [AppAuthGuard] },
  { path: 'logout', component: LogoutComponent},
  { path: '**', redirectTo: '/ebooks'}
]);

@NgModule({
  declarations: [
    AppComponent,
    LogoutComponent
  ],
  imports: [
    CommonModule,
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
  }, VersionDataSource],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function initKeycloak(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => keycloak.init(
        {
          config: environment.keycloak,
          initOptions: {
            checkLoginIframe: false
          },
          bearerExcludedUrls: []
        }
    );
}
