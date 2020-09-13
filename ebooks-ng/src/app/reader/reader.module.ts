import { NgModule } from '@angular/core';
import { ModelModule } from '../model/model.module';
import { EbooksListComponent } from './ebooksList.component';
import { ReaderComponent } from './reader.component';
import { BookLinkComponent } from './bookLink.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CommonModule as ApplicationCommonModule } from '../common/common.module';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [ApplicationCommonModule, ModelModule, RouterModule, CommonModule, NgxExtendedPdfViewerModule, FormsModule, NgbModule],
  declarations: [EbooksListComponent, ReaderComponent, BookLinkComponent]
})
export class ReaderModule {}
