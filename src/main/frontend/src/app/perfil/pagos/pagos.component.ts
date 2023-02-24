import {Component, OnInit} from '@angular/core';
import {PerfilService} from "../../services/perfil.service";

@Component({
  selector: 'app-pagos',
  templateUrl: './pagos.component.html',
  styleUrls: ['./pagos.component.css']
})
export class PagosComponent implements OnInit {

  constructor(
    public perfilService: PerfilService
  ) {}

  precio: number

  ngOnInit(): void {
    this.perfilService.emprendimiento.subscribe(emprendimiento => {
      if (emprendimiento)
        this.precio = emprendimiento.precioManguito
    })
  }

  updatePrecio() {
    this.perfilService.actualizarDatos({ precioManguito: this.precio }).subscribe()
  }
}
