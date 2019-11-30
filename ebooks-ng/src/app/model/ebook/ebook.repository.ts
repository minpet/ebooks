import { Ebook } from './ebook.model';
import { EbookRegistration } from './ebookRegistration.model';
import { EbookDataSource } from './ebook.datasource';
import { Injectable } from '@angular/core';

@Injectable()
export class EbookRepository {
  private ebooks: Ebook[] = [];
  public populated = false;

  constructor(private dataSource: EbookDataSource) {
    this.prepare().then(result => { });
  }

  public prepare(): Promise<any> {
    return new Promise<any>(resolve => {
      if (this.populated) {
        resolve(true);
      } else {
        this.dataSource.getEbooks().subscribe(data => {
          this.ebooks = data;
          this.populated = true;
          resolve(true);
        });
      }
    });
  }

  public registerEbook(ebookRegistration: EbookRegistration): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.dataSource.registerEbook(ebookRegistration).then(ebook => {
        this.ebooks.push(ebook);
        resolve(true);
      });
    });
  }

  getEbooks(): Ebook[] {
    return this.ebooks;
  }

  public updateSelectedPage(selectedEbook: Ebook, selectedPage: number) {
    this.dataSource.updateSelectedPage(selectedEbook.id, selectedPage).subscribe(res => { });
  }
}
