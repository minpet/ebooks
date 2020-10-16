import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Component({
  template: ''
})
export class LogoutComponent {
  constructor(private router: Router, private keycloak: KeycloakService) {
      this.router.navigateByUrl('/ebooks').then(() => { this.keycloak.logout()});
  }
}
