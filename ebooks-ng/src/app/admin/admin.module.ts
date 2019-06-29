import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReaderModule } from './../reader/reader.module';
import { EbookEditorComponent } from './ebookEditor.component';
import { AdminActionsComponent } from './adminActions.component';
import { FileCandidatesComponent } from './fileCandidates.component';
import { RefreshFileCandidateDataGuard } from './guards/refreshFileCandidatesData.guard';
import { FileCandidateRefresher } from './service/fileCandidateRefresher';
import { FileCandidatesFirstGuard } from './guards/fileCandidatesFirst.guard';
import { FileCandidateRegistrationComponent } from './fileCandidateRegistration.component';
import { FormsModule } from '@angular/forms';
import { CommonModule as ApplicationCommonModule } from '../common/common.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

const routes = RouterModule.forChild([
  { path: 'ebook/edit/:id', component: EbookEditorComponent },
  { path: 'actions', component: AdminActionsComponent },
  { path: 'fileCandidates/register/:hashedName', component: FileCandidateRegistrationComponent, canActivate: [FileCandidatesFirstGuard]},
  { path: 'fileCandidates', component: FileCandidatesComponent, canActivate : [RefreshFileCandidateDataGuard, FileCandidatesFirstGuard]},
  { path: '**', redirectTo: 'actions'}
]);

@NgModule({
  imports: [CommonModule, routes, FormsModule, ApplicationCommonModule, NgbModule],
  declarations: [AdminActionsComponent, EbookEditorComponent, FileCandidatesComponent, FileCandidateRegistrationComponent],
  providers: [RefreshFileCandidateDataGuard, FileCandidateRefresher, FileCandidatesFirstGuard]
})
export class AdminModule { };

