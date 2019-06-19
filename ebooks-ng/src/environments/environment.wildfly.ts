import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
const keycloakConfig: KeycloakConfig = {
  url: 'http://172.18.0.22:8080/auth',
  realm: 'devMiniApps',
  clientId: 'angular-client',
  'credentials': {
    'secret': 'a19de592-adfb-4217-b554-86f3d85b1a6a'
  }
};

export const environment = {
  production: false,
  keycloak : keycloakConfig,
restEbookBaseURL: `http://${location.hostname}:8080/ebooks-rest/ebook`,
restFileCandidateBaseURL: `https://${location.hostname}:8080/ebooks-rest/fileCandidate`
};
