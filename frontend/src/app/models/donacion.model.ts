import {Plan} from "./plan.model";

export class Donacion {
  nombre: string;
  monto: number;
  contacto: string;
  mensaje: string;
  fecha: Date;
}

export class DonacionManguito extends Donacion {
  cantidad: number;
}

export class Suscripcion extends Donacion {
  plan: Plan
}

export class ResponseDonaciones {
  manguitos: DonacionManguito[]
  suscripciones: Suscripcion[]
}
