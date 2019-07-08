import { Component } from '@angular/core';
import { RestStatsDataCache } from './restStats.datacache';
import { Report } from './report.model';

@Component({
  templateUrl: './restStats.component.html'
})
export class RestStatsComponent {

  constructor(private dataCache: RestStatsDataCache) { }

  get report(): Report {
    return this.dataCache._report;
  }
}
