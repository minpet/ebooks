import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { RestStatsDataSource } from './restStats.datasource';
import { RestStatsDataCache } from './restStats.datacache';

@Injectable()
export class RefreshReportGuard {

  constructor(private dataSource: RestStatsDataSource, private dataCache: RestStatsDataCache) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

    this.dataSource.getStats().subscribe(reportText => {
      this.dataCache._report.text = reportText;
    });

    return true;
  }
}
