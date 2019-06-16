import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuLink } from './model/common/menuLink.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private links: MenuLink[] = [];

  constructor(private router: Router) {
    this.links = [new MenuLink('Ebooks', '/ebooks'),
                  new MenuLink('Admin', '/admin')];
  }

  get menuLinks(): MenuLink[] {
    return this.links;
  }

  changeMenu(menu: MenuLink) {
    if (menu) {
      this.router.navigateByUrl(menu.target);
    }
  }

  public adminMenu = new MenuLink('Admin', '/admin');
}
