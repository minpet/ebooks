import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReaderModule } from './../reader/reader.module';
import { EbookEditorComponent } from './ebookEditor.component';
import { AdminActionsComponent } from './adminActions.component';
import { FileCandidatesComponent } from './fileCandidates.component';
import { RefreshFileCandidateDataGuard } from './guards/refreshFileCandidatesData.guard';
import { FileCandidateRefresher } from './service/fileCandidateRefresher';

const routes = RouterModule.forChild([
  { path: 'ebook/{mode}/{id}', component: EbookEditorComponent },
  { path: 'actions', component: AdminActionsComponent },
  { path: 'fileCandidates', component: FileCandidatesComponent, canActivate : [RefreshFileCandidateDataGuard]},
  { path: '**', redirectTo: 'actions'}
]);

@NgModule({
  imports: [CommonModule, RouterModule, routes],
  declarations: [AdminActionsComponent, EbookEditorComponent, FileCandidatesComponent],
  providers: [RefreshFileCandidateDataGuard, FileCandidateRefresher]
})
export class AdminModule { };

