import { TokenHolder } from '../../security/token.holder';
import { Ebook } from './ebook.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EbookRegistration } from './ebookRegistration.model';

@Injectable()
export class EbookDataSource {
  private baseUrl: string = environment.restEbookBaseURL;

  constructor(private http: HttpClient, private tokenHolder: TokenHolder) { }

  getEbooks(): Observable<Ebook[]> {
    return this.http.get<Ebook[]>(this.baseUrl);
  }

  saveEbook(ebook: Ebook): Observable<Ebook> {

    this.tokenHolder.getToken().then(data => {
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };

      httpOptions.headers.set('Authorization', `Bearer ${data}`);

      return this.http.put<Ebook>(this.baseUrl, ebook, httpOptions);
    });

    return null;
  }

  registerEbook(ebookRegistration: EbookRegistration): Promise<Ebook> {
    return new Promise<Ebook>((resolve) => {

      this.tokenHolder.getToken().then(data => {
        const httpOptions = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${data}`
          })
        };
        this.http.post<Ebook>(environment.restEbookRegisterBaseURL, ebookRegistration, httpOptions).subscribe(ebook => {
          resolve(ebook);
        });
      });
    });
  }
}
