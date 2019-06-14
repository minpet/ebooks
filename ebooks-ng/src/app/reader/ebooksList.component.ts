import {Component} from '@angular/core';
import { Ebook } from '../model/ebook/ebook.model';
import { EbookRepository } from '../model/ebook/ebook.repository';

@Component({
  templateUrl: './ebooksList.component.html'
})
export class EbooksListComponent {

  constructor(private repo: EbookRepository) { }

  get ebooks(): Ebook[]{
    return this.repo.getEbooks();
  }
}
