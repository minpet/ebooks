import {NgModule} from '@angular/core';
import {CounterDirective } from './counter.directive';

@NgModule({
  declarations: [CounterDirective],
  providers: [],
  exports: [CounterDirective]
})
export class CommonModule { }
