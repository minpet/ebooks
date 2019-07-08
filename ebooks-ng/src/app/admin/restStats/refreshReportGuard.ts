import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { RestStatsDataSource } from './restStats.datasource';

@Injectable()
export class RefreshReportGuard {

  constructor(private dataSource: RestStatsDataSource) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

    this.dataSource.getStats().subscribe(reportText => {
      console.log(reportText);
    });

    return true;
  }
}
