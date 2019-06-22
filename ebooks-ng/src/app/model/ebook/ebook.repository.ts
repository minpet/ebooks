import {Ebook} from './ebook.model';
import { EbookDataSource } from './ebook.datasource';
import { Injectable } from '@angular/core';

@Injectable()
export class EbookRepository {
  private ebooks: Ebook[] = [];
  public populated = false;

  constructor(private dataSource: EbookDataSource) {
    dataSource.getEbooks().subscribe(data => {
      this.ebooks = data;
      this.populated = true;
    });
  }

  getEbooks(): Ebook[] {
    return this.ebooks;
  }
}
