import { AbstractRepository } from '../common/AbstractRepository';
import { Ebook } from './ebook.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable()
export class EbookDataSource extends AbstractRepository {
  private baseUrl: string = environment.restEbookBaseURL;
  constructor(private http: HttpClient) { super(); }

  getEbooks(): Observable<Ebook[]> {
    return this.http.get<Ebook[]>(this.baseUrl);
  }

  saveEbook(ebook: Ebook): Observable<Ebook> {
    return this.http.put<Ebook>(this.baseUrl, ebook, super.getOptions());
  }
}
