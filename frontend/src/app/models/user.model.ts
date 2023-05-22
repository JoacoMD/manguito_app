import jwtDecode from "jwt-decode";

export class User {
  constructor(
    public mail: string,
    public emprendimientoUrl: string,
    private _token: string,
    private _expirationDate: Date,
  ) {
    const decodedToken = jwtDecode(_token)
    // @ts-ignore
    this.rol = decodedToken.role
  }

  rol: string

  get token() {
    if (!this._expirationDate || new Date() > this._expirationDate) {
      return null
    }
    return this._token
  }
}
