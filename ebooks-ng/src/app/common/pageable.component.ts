export abstract class PageableComponent {
  public selectedPage = 1;
  public itemsPerPage = 10;

  changePage(newPage: number) {
    this.selectedPage = newPage;
  }

  changePageSize(newPageSize) {
    this.itemsPerPage = newPageSize;
    this.changePage(1);
  }

  get pageNumbers(): number {
    return Math.ceil(this.getDataArray().length / this.itemsPerPage);
  }

  protected getSelectedPageData(): any {
    const pageIndex = (this.selectedPage - 1) * this.itemsPerPage;
    return this.getDataArray().slice(pageIndex, pageIndex + this.itemsPerPage);
  }

  protected abstract getDataArray(): any
}
