import {Component} from '@angular/core';
import { Ebook } from '../model/ebook/ebook.model';
import { EbookRepository } from '../model/ebook/ebook.repository';

@Component({
  templateUrl: './ebooksList.component.html'
})
export class EbooksListComponent {

  public selectedPage = 1;
  public itemsPerPage = 10;

  constructor(private repo: EbookRepository) { }

  get ebooks(): Ebook[]{
    const pageIndex = (this.selectedPage - 1) * this.itemsPerPage;
    return this.repo.getEbooks().slice(pageIndex, pageIndex + this.itemsPerPage);
  }

  changePage(newPage: number) {
    this.selectedPage = newPage;
  }

  changePageSize(newPageSize) {
    this.itemsPerPage = newPageSize;
    this.changePage(1);
  }

  get pageNumbers(): number {
    return Math.ceil(this.repo.getEbooks().length / this.itemsPerPage);
  }


}
