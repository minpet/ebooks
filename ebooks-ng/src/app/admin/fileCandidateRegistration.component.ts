import { Component } from '@angular/core';
import { EbookRegistration } from '../model/ebook/ebookRegistration.model';
import { Ebook } from '../model/ebook/ebook.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import { EbookRepository } from '../model/ebook/ebook.repository';

@Component({
  templateUrl: './fileCandidateRegistration.component.html'
})
export class FileCandidateRegistrationComponent {
  ebookRegistration: EbookRegistration = new EbookRegistration();

  constructor(private router: Router, private activeRoute: ActivatedRoute, private repo: EbookRepository) {
    this.ebookRegistration.underlyingFileName = activeRoute.snapshot.params['hashedName'];
  }


  submitForm(form: NgForm) {
    if (form.valid) {
      this.repo.registerEbook(this.ebookRegistration).then(isOK => {

      });
    }
  }
}
