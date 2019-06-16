import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
let keycloakConfig: KeycloakConfig = {
  url: 'http://build-machine.local/auth',
  realm: 'your-realm-name',
  clientId: 'your-client-id',
  "credentials": {
    "secret": "your-client-secret"
  }
};

export const environment = {
  production: true,
  restEbookBaseURL: `https://${location.hostname}/ebooks-rest/ebook`,
  restFileCandidateBaseURL: `https://${location.hostname}/ebooks-rest/fileCandidate`,
  keycloak: keycloakConfig,
};
