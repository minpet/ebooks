import { Component } from '@angular/core';
import { Ebook } from '../model/ebook/ebook.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  templateUrl: './fileCandidateRegistration.component.html'
})
export class FileCandidateRegistrationComponent {
  ebook: Ebook = new Ebook();

  constructor(private router: Router, private activeRoute: ActivatedRoute) {
    this.ebook.underlyingFileName = activeRoute.snapshot.params['hashedName'];
  }
}
