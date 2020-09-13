// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
const keycloakConfig: KeycloakConfig = {
  url: 'http://172.18.0.22:8080/auth',
  realm: 'devMiniApps',
  clientId: 'angular-client',
  'credentials': {
     'secret': 'a19de592-adfb-4217-b554-86f3d85b1a6a'
    // 'secret': '8b244e6d-4ec1-4d77-9c13-916be8e3824f'
  }
};

export const environment = {
  production: false,
  keycloak : keycloakConfig,
  restEbookBaseURL: 'http://localhost:3500/ebook',
  restEbookRegisterBaseURL: 'http://172.18.0.21:8080/ebooks-rest/admin/ebookRegister',
  restFileCandidateBaseURL: 'http://localhost:3500/fileCandidates',
  restStatsBaseURL: 'http://172.18.0.21:8080/ebooks-rest/resteasy/registry',
  restEbookSelectedPage: `http://172.18.0.21:8080/ebookSelectedPage`,
  restVersionBaseUrl: 'http://localhost:3500/version',
  restEbookSetCoverBaseURL: 'http://undefined'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
