export class EbookRegistration {
  constructor(public hashedName?: string, public name?: string, public autoIndex?: boolean) {
    this.autoIndex = false;
  }
}
