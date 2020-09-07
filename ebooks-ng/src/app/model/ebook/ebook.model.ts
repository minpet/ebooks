export class Ebook {
  constructor(public id?: number,
    public name?: string,
    public uri?: string,
    public selectedPage?: number,
    public underlyingFileName?: string
  ) { }
}
