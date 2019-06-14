import { NgModule } from '@angular/core';
import { EbookRepository } from './ebook/ebook.repository';
import { EbookDataSource } from './ebook/ebook.datasource';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports : [HttpClientModule],
  providers : [EbookRepository, EbookDataSource]
})
export class ModelModule { }
