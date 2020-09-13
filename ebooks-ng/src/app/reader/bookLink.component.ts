import { Component, Input } from '@angular/core';
import { Ebook } from '../model/ebook/ebook.model';
import { KeycloakService } from 'keycloak-angular';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgForm } from '@angular/forms';
import { EbookDataSource } from '../model/ebook/ebook.datasource';

@Component({
  templateUrl: './bookLink.component.html',
  styleUrls: ['./bookLink.component.css'],
  selector: 'book-link'
})
export class BookLinkComponent {
  @Input('ebook')
  private _ebook: Ebook

  private _loggedIn = false;
  private file: any;

  constructor(private keycloak: KeycloakService, private modalService: NgbModal, private datasource: EbookDataSource) {
    this.keycloak.isLoggedIn().then(loggedIn => {
      this._loggedIn = loggedIn;
    });
  }

  get ebook(): Ebook {
    return this._ebook;
  }

  get loggedIn(): boolean {
    return this._loggedIn;
  }

  public open(content, ebook: Ebook) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {

    });
  }

  public submitForm(ngForm: NgForm) {
    if (ngForm.valid) {
      let fileContent: any = null;

      if (this.file !== undefined) {
        const fileReader = new FileReader();
        fileReader.onload = (e) => {
          fileContent = fileReader.result;
          this.processReq(fileContent);
        }
        fileReader.readAsBinaryString(this.file);
      } else {
        this.processReq(fileContent)
      }
    }
  }

  private processReq(fileContent: any) {
    this.datasource.setCover(this._ebook.id, fileContent).then(ebook => {
      if (ebook) {
        //refresh from uploaded data
        this.ebook.imageUrl = ebook.imageUrl;
      }
    });
  }

  public onCoverBookFile(event) {
    this.file = event.target.files[0];
    console.log(this.file);
  }
 }
