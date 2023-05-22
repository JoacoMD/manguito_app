export class Imagen {
  constructor(
    private _src: string,
    public type: string
  ) {
    this._src = _src.replace(/^data:image\/[a-z]+;base64,/, "")
      .replace(/^dataimage\/[a-z]+base64/, "");
  }

  get src() {
    if (!this._src) return 'https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png'
    return `data:${this.type};base64, ${this._src}`;
  }

  get rawSrc() {
    return this._src
  }
}
