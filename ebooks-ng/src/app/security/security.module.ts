import { NgModule } from '@angular/core';
import { AppAuthGuard } from './appAuth.guard';
import { KeycloakService } from 'keycloak-angular';

@NgModule({
  declarations: [],
  providers: [AppAuthGuard, KeycloakService]
})
export class SecurityModule { };
