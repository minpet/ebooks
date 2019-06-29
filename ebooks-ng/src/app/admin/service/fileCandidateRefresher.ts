import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FileCandidate } from '../../model/fileCandidate/fileCandidate.model';
import { environment } from '../../../environments/environment';
import { TokenHolder } from './../../security/token.holder';
import { FileCandidateRepository } from '../../model/fileCandidate/fileCandidate.repository';

@Injectable()
export class FileCandidateRefresher {
  constructor(private http: HttpClient, private tokenHolder: TokenHolder, private repo: FileCandidateRepository) { }

  refreshData() {
    const token = this.tokenHolder.getToken();

    token.then(data => {

      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${data}`
        })
      };

      this.http.get<FileCandidate[]>(environment.restFileCandidateBaseURL, httpOptions).subscribe(candidates => {
        this.repo.updateWithRemoteData(candidates);
      });
    });
  }

  deleteAndRefresh(candidate: FileCandidate) {
    this.tokenHolder.getToken().then(data => {
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${data}`
        })
      };

      this.http.delete(environment.restFileCandidateBaseURL + '/' + candidate.hashedName, httpOptions).subscribe(deleted => {
      this.http.get<FileCandidate[]>(environment.restFileCandidateBaseURL, httpOptions).subscribe(candidates => {
        this.repo.updateWithRemoteData(candidates);
      });
     });
    });
  }
}
