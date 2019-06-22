import { NgModule } from '@angular/core';
import { AppAuthGuard } from './appAuth.guard';
import { KeycloakService } from 'keycloak-angular';
import { TokenHolder } from './token.holder';

@NgModule({
  declarations: [],
  providers: [AppAuthGuard, KeycloakService, TokenHolder]
})
export class SecurityModule { };
