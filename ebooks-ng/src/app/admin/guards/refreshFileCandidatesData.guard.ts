import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { FileCandidateRefresher } from './../service/fileCandidateRefresher';

@Injectable()
export class RefreshFileCandidateDataGuard {
  constructor(private refresher: FileCandidateRefresher) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    this.refresher.refreshData();
    return true;
  }
}
