import {RedSocial} from "./red-social.model";
import {Plan} from "./plan.model";

export class Emprendimiento {
  constructor(
    public nombreEmprendimiento: string,
    public imagenPerfil: any,
    public banner: any,
    public url: string,
    public descripcion: string,
    public categorias: string[],
    public precioManguito: number,
    public mostrarTopDonadores: boolean,
    public ocultarManguitosRecibidos: boolean,
    public manguitosRecibidos: number,
    public redesSociales: RedSocial[],
    public planes: Plan[]
  ) {
  }
}
