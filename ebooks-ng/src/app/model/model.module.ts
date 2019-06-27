import { NgModule } from '@angular/core';
import { EbookRepository } from './ebook/ebook.repository';
import { EbookDataSource } from './ebook/ebook.datasource';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule as ApplicationCommonModule } from '../common/common.module';
import { FileCandidateRepository } from './fileCandidate/fileCandidate.repository';

@NgModule({
  imports: [HttpClientModule, ApplicationCommonModule],
  providers: [EbookRepository, EbookDataSource, FileCandidateRepository]
})
export class ModelModule { }
