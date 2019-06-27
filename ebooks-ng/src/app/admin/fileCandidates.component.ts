import { Component } from '@angular/core';
import { FileCandidateRepository } from '../model/fileCandidate/fileCandidate.repository';
import { FileCandidate } from '../model/fileCandidate/fileCandidate.model';
import { PageableComponent } from '../common/pageable.component';

@Component({
  templateUrl: './fileCandidates.component.html'
})
export class FileCandidatesComponent extends PageableComponent {
  constructor(private repo: FileCandidateRepository) { super(); }

  get fileCandidates(): FileCandidate[] {
    return super.getSelectedPageData();
  }

  protected getDataArray(): Object[] {
    return this.repo.getFileCandidates();
  }
}
