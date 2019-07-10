import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable()
export class RestStatsDataSource {

  constructor(private http: HttpClient) { }

  getStats(): Observable<string> {
   return this.http.get(environment.restStatsBaseURL, {responseType: 'text', headers: {'Accept' : 'application/xml'}});
  }
}
