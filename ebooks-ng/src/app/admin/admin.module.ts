import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReaderModule } from './../reader/reader.module';
import { EbookEditorComponent } from './ebookEditor.component';
import { AdminActionsComponent } from './adminActions.component';

const routes = RouterModule.forChild([
  { path: 'ebook/{mode}/{id}', component: EbookEditorComponent },
  { path: 'actions', component: AdminActionsComponent },
  { path: '**', redirectTo: 'actions'}
]);

@NgModule({
  imports: [RouterModule, routes],
  declarations: [AdminActionsComponent, EbookEditorComponent],
  providers: []
})
export class AdminModule { };

