export class EbookRegistration {
  constructor(public underlyingFileName?: string, public name?: string, public autoIndex?: boolean) {
    this.autoIndex = false;
  }
}
