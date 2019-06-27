import { Injectable } from '@angular/core';
import { FileCandidate } from './fileCandidate.model';

@Injectable()
export class FileCandidateRepository {
  private candidates: FileCandidate[] = [];

  updateWithRemoteData(dataFromServer: FileCandidate[]) {
    // optimize to only process diff of local/server collection
    this.candidates = dataFromServer;
  }

  getFileCandidates(): FileCandidate[] {
    return this.candidates;
  }
}
