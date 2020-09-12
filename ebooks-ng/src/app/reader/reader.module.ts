import { NgModule } from '@angular/core';
import { ModelModule } from '../model/model.module';
import { EbooksListComponent } from './ebooksList.component';
import { ReaderComponent } from './reader.component';
import { BookLinkComponent } from './bookLink.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CommonModule as ApplicationCommonModule } from '../common/common.module';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';

@NgModule({
  imports: [ApplicationCommonModule, ModelModule, RouterModule, CommonModule, NgxExtendedPdfViewerModule],
  declarations: [EbooksListComponent, ReaderComponent, BookLinkComponent]
})
export class ReaderModule {}
