import { NgModule } from '@angular/core';
import { ModelModule } from '../model/model.module';
import { EbooksListComponent } from './ebooksList.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [ModelModule, RouterModule, CommonModule],
  declarations: [EbooksListComponent]
})
export class ReaderModule {}
