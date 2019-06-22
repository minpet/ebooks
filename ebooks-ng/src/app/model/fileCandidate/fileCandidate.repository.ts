import { Injectable } from '@angular/core';
import { FileCandidate } from './fileCandidate.model';

@Injectable()
export class FileCandidateRepository {
  private candidates: FileCandidate[] = [];

}
