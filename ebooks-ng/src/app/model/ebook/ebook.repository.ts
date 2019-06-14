import {Ebook} from './ebook.model';
import { EbookDataSource } from './ebook.datasource';
import { Injectable } from '@angular/core';

@Injectable()
export class EbookRepository {
  private ebooks: Ebook[] = [];

  constructor(private dataSource: EbookDataSource) {
    dataSource.getEbooks().subscribe(data => {
      this.ebooks = data;
    });
  }

  getEbooks(): Ebook[]{
    return this.ebooks;
  }
}
