import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable()
export class RestStatsDataSource {

  constructor(private http: HttpClient) { }

  getStats(): Observable<string> {
    const httpConfig = {
      headers: new HttpHeaders({
        'Content-Type': 'application/xml'
      })
    };

    return this.http.get<string>(environment.restStatsBaseURL, httpConfig);
  }
}
