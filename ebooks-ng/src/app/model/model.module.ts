import { NgModule } from '@angular/core';
import { EbookRepository } from './ebook/ebook.repository';
import { EbookDataSource } from './ebook/ebook.datasource';
import { HttpClientModule } from '@angular/common/http';
import { CounterDirective } from './common/counter.directive';

@NgModule({
  imports: [HttpClientModule],
  declarations: [CounterDirective],
  providers: [EbookRepository, EbookDataSource],
  exports: [CounterDirective]
})
export class ModelModule { }
