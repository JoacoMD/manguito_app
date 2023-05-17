export class StatusDonacionModel {
  constructor(
    public externalReference: string,
    public nombreEmprendimiento: string,
    public urlEmprendimiento: string,
    public montoTotal: number,
    public cantidadManguitos: number,
    public estado: string
  ) {
  }
}
