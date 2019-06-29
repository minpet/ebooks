import { Component } from '@angular/core';
import { FileCandidateRepository } from '../model/fileCandidate/fileCandidate.repository';
import { FileCandidate } from '../model/fileCandidate/fileCandidate.model';
import { PageableComponent } from '../common/pageable.component';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { FileCandidateRefresher } from './service/fileCandidateRefresher';

@Component({
  templateUrl: './fileCandidates.component.html'
})
export class FileCandidatesComponent extends PageableComponent {
  constructor(private repo: FileCandidateRepository, private modalService: NgbModal, private refresher: FileCandidateRefresher) { super(); }

  private _selectedCandidate: FileCandidate;
  private selectedPopOverCandidate: FileCandidate;

  get fileCandidates(): FileCandidate[] {
    return super.getSelectedPageData();
  }

  protected getDataArray(): Object[] {
    return this.repo.getFileCandidates();
  }

  confirmDelete(candidate: FileCandidate) {

  }

  open(content, candidate) {
    this._selectedCandidate = candidate;
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      if (result === 'YES') {
        this.refresher.deleteAndRefresh(candidate);
      }
    }, (reason) => {
      // console.log(`Dismissed ${this.getDismissReason(reason)}`);
    });
  }

  selectPopOver(candidate: FileCandidate) {
    this.selectedPopOverCandidate = candidate;
  }

  get popOverCandidate(): FileCandidate{
    return this.selectedPopOverCandidate;
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  get selectedCandidate(): FileCandidate {
    return this._selectedCandidate;
  }
}
