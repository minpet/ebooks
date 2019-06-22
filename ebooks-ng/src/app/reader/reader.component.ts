import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ebook } from './../model/ebook/ebook.model';
import { EbookRepository } from './../model/ebook/ebook.repository';

@Component({
  templateUrl: './reader.component.html'
})
export class ReaderComponent {

  constructor(private repo: EbookRepository, private route: ActivatedRoute) {}

  get ready() {
    return this.repo.populated;
  }

  get selectedEbook(): Ebook {
     const selectedId: number = Number(this.route.snapshot.params['id']);
    return this.repo.getEbooks().find(ebook => ebook.id === selectedId);
  }
}
