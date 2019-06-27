import {Component} from '@angular/core';
import { Ebook } from '../model/ebook/ebook.model';
import { EbookRepository } from '../model/ebook/ebook.repository';
import { PageableComponent } from '../common/pageable.component';

@Component({
  templateUrl: './ebooksList.component.html'
})
export class EbooksListComponent extends PageableComponent {

  constructor(private repo: EbookRepository) {
    super();
  }

  get ebooks(): Ebook[]{
    return super.getSelectedPageData();
  }

  protected getDataArray(): Object[]{
    return this.repo.getEbooks();
  }


}
