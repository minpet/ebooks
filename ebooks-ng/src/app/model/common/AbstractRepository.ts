import { HttpHeaders } from '@angular/common/http';

export class AbstractRepository {
  protected auth_token: string;

  protected getOptions() {
    return {
      headers: new HttpHeaders({'Authorization': `Bearer<${this.auth_token}>`})
    };
  }
}
