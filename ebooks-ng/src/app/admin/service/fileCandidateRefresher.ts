import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FileCandidate } from '../../model/fileCandidate/fileCandidate.model';
import { environment } from '../../../environments/environment';
import { TokenHolder } from './../../security/token.holder';

@Injectable()
export class FileCandidateRefresher {
  constructor(private http: HttpClient, private tokenHolder: TokenHolder) { }

  refreshData() {
    const token = this.tokenHolder.getToken();

    token.then(data => {
      console.log(data);

      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        'Authorization': `Bearer ${data}`
        })
      };

      console.log(httpOptions);

      this.http.get<FileCandidate[]>(environment.restFileCandidateBaseURL, httpOptions).subscribe(data => {
        console.log(data);
      });
    })
  }
}
