import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ebook } from './../model/ebook/ebook.model';
import { EbookRepository } from './../model/ebook/ebook.repository';

@Component({
  templateUrl: './reader.component.html'
})
export class ReaderComponent {
  public selectedEbook: Ebook;

  constructor(private repo: EbookRepository, private route: ActivatedRoute) {
    const selectedId: number = Number(route.snapshot.params['id']);
    this.selectedEbook = repo.getEbooks().find(ebook => ebook.id === selectedId);
  }


}
