export class FileCandidate {
  constructor(public fileName: string, public hashedName: string, public conflicts: string[]) { }
}
