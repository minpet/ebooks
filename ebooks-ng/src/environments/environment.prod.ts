import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
let keycloakConfig: KeycloakConfig = {
  url: 'http://build-machine.local/auth',
  realm: 'WildflyMiniApps',
  clientId: 'Angular-client',
  "credentials": {
    "secret": "1fa36aad-4783-49fe-b0bb-5b7ad791a233"
  }
};

export const environment = {
  production: true,
  restEbookBaseURL: `https://${location.hostname}/ebooks-rest/ebook`,
  restFileCandidateBaseURL: `https://${location.hostname}/ebooks-rest/fileCandidate`,
  keycloak: keycloakConfig,
};
