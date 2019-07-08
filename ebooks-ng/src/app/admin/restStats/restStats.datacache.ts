import { Injectable } from '@angular/core';
import { Report } from './report.model';

@Injectable()
export class RestStatsDataCache {
  public _report: Report = new Report('');
}
