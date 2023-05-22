import {Component, OnInit} from '@angular/core';
import {PerfilService} from "../../services/perfil.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-pagos',
  templateUrl: './pagos.component.html',
  styleUrls: ['./pagos.component.css']
})
export class PagosComponent implements OnInit {

  constructor(
    public perfilService: PerfilService,
    private _snackbar: MatSnackBar
  ) {}

  precio: number
  updating: boolean = false

  ngOnInit(): void {
    this.perfilService.emprendimiento.subscribe(emprendimiento => {
      if (emprendimiento)
        this.precio = emprendimiento.precioManguito
    })
  }

  updatePrecio() {
    if (this.precio && this.precio > 1)
      this.updating = true
      this.perfilService.actualizarDatos({ precioManguito: this.precio })
        .subscribe((res) => {
          if (res) {
            this.updating = false
            this._snackbar.open('Datos actualizados correctamente', null, {duration: 2000})
          }
        })
  }
}
