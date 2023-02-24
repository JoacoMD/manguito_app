import {Component, OnInit} from '@angular/core';
import {Emprendimiento} from "../../models/emprendimiento.model";
import {EmprendimientoService} from "../../services/emprendimiento.service";
import {ActivatedRoute} from "@angular/router";
import {Imagen} from "../../models/Imagen";

@Component({
  selector: 'app-detalle-emprendimiento',
  templateUrl: './detalle-emprendimiento.component.html',
  styleUrls: ['./detalle-emprendimiento.component.css']
})
export class DetalleEmprendimientoComponent implements OnInit {

  constructor(
    private empService: EmprendimientoService,
    private route: ActivatedRoute
  ) {
  }

  emprendimiento: Emprendimiento
  banner: Imagen
  imagenPerfil: Imagen
  loading: boolean = false

  ngOnInit(): void {
    this.loading = true
    this.route.params.subscribe(params => {
      this.empService.getEmprendimiento(params['url'])
        .subscribe(emp => {
          this.emprendimiento = emp
          this.banner = emp.banner && new Imagen(emp.banner?.archivo, emp.banner?.extension)
          this.imagenPerfil = emp.imagenPerfil ? new Imagen(emp.imagenPerfil?.archivo, emp.imagenPerfil?.extension) : new Imagen('', ''),
          this.loading = false
        })
    })
  }

}
