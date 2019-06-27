import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { KeycloakService } from 'keycloak-angular';

@Injectable()
export class TokenHolder {
  constructor(private keycloakAngular: KeycloakService) {}

  public getToken(): Promise<string> {
    if (this.keycloakAngular.isLoggedIn()) {
      return this.keycloakAngular.getToken();
    } else {
      console.log('token not aquired, user not logged in');
      return null;
    }
  }
}
