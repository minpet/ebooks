import { Component, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ebook } from './../model/ebook/ebook.model';
import { EbookRepository } from './../model/ebook/ebook.repository';

@Component({
  templateUrl: './reader.component.html'
})
export class ReaderComponent implements OnDestroy{

  private _selectedPage = 0;
  private _selectedEbook: Ebook;

  constructor(private repo: EbookRepository, private route: ActivatedRoute) {
    const selectedId: number = Number(this.route.snapshot.params['id']);
    const ebook = this.repo.getEbooks().find(ebook => ebook.id === selectedId);
    this._selectedEbook = ebook;
    this._selectedPage = ebook.selectedPage;
  }

  get ready() {
    const ready = this.repo.populated;
    return ready;
  }

  get selectedEbook(): Ebook {
    return this._selectedEbook;
  }

  get selectedPage(): number {
    return this._selectedPage;
  }

  set selectedPage(page: number) {
    this._selectedPage = page;
  }

  public updateSelectedPage() {
    this.repo.updateSelectedPage(this._selectedEbook, this._selectedPage);
  }

  public ngOnDestroy() {
    this._selectedEbook.selectedPage = this._selectedPage;
  }
}
