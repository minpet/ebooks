import { Component, Input } from '@angular/core';
import { Ebook } from '../model/ebook/ebook.model';

@Component({
  templateUrl: './bookLink.component.html',
  styleUrls: ['./bookLink.component.css'],
  selector: 'book-link'
})
export class BookLinkComponent {
  @Input('ebook')
  private _ebook: Ebook

  constructor() { }

  get ebook(): Ebook {
    return this._ebook;
  }
 }
