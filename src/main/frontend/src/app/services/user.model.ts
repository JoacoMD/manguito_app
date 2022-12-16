export class User {
  constructor(
    public username: string,
    public authorities: { authority: string }[]
  ) {}
}
