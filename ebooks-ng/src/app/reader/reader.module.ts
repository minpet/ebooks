import { NgModule } from '@angular/core';
import { ModelModule } from '../model/model.module';
import { EbooksListComponent } from './ebooksList.component';
import { ReaderComponent } from './reader.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';

@NgModule({
  imports: [ModelModule, RouterModule, CommonModule, NgxExtendedPdfViewerModule],
  declarations: [EbooksListComponent, ReaderComponent]
})
export class ReaderModule {}
