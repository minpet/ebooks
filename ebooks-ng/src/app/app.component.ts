import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuLink } from './model/common/menuLink.model';
import { VersionDataSource } from './version/version.datasource';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private links: MenuLink[] = [];
  public selectedMenu: MenuLink = null;
  private _version = '';

  constructor(private router: Router, private versionDataSource: VersionDataSource) {
    this.links = [new MenuLink('Ebooks', '/ebooks'),
                  new MenuLink('Admin', '/admin')];
    this.versionDataSource.getVersion().subscribe(versionText => {
      this._version = versionText;
    })
  }

  get menuLinks(): MenuLink[] {
    return this.links;
  }

  changeMenu(menu: MenuLink) {
    if (menu) {
      this.selectedMenu = menu;
      this.router.navigateByUrl(menu.target);
    }
  }

  get version(){
    return this._version;
   }
}
