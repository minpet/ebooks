import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
const keycloakConfig: KeycloakConfig = {
  url: 'https://build-machine.local/auth',
  realm: 'WildflyMiniApps',
  clientId: 'angular-client',
  'credentials': {
    'secret': '2f78fed4-29f0-4aed-982a-d8d9792c8ffe'
  }
};

export const environment = {
  production: true,
  keycloak: keycloakConfig,
  restEbookBaseURL: `https://${location.hostname}/ebooks-rest/ebook`,
  restEbookRegisterBaseURL: `https://${location.hostname}/ebooks-rest/admin/ebookRegister`,
  restFileCandidateBaseURL: `https://${location.hostname}/ebooks-rest/admin/fileCandidate`,
  restStatsBaseURL: `https://${location.hostname}/ebooks-rest/resteasy/registry`,
  restVersionBaseUrl: `https://${location.hostname}/ebooks-rest/version/text`,
  restEbookSelectedPage: `https://${location.hostname}/ebooks-rest/ebookSelectedPage`,
  restEbookSetCoverBaseURL: `https://${location.hostname}/ebooks-rest/admin/ebookImage/upload`,
};
