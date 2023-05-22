import { Component } from '@angular/core';
import {DonacionService} from "../services/donacion.service";
import {PerfilService} from "../services/perfil.service";

@Component({
  selector: 'app-donaciones',
  templateUrl: './donaciones.component.html',
  styleUrls: ['./donaciones.component.scss']
})
export class DonacionesComponent {

  constructor(
    private perfilService: PerfilService,
    private donacionService: DonacionService
  ) {
    this.donacionService.getDonacionesByEmprendimientoPropio()
  }

}
