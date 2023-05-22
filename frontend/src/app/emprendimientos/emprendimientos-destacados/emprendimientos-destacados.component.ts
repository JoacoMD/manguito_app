import { Component } from '@angular/core';
import { Emprendimiento } from 'src/app/models/emprendimiento.model';
import { EmprendimientoService } from 'src/app/services/emprendimiento.service';

@Component({
  selector: 'app-emprendimientos-destacados',
  templateUrl: './emprendimientos-destacados.component.html',
  styleUrls: ['./emprendimientos-destacados.component.scss']
})
export class EmprendimientosDestacadosComponent {

  emprendimientos: Emprendimiento[] = []

  constructor(
    private empService: EmprendimientoService
  ) {
    this.empService.getEmprendimientosDestacados().subscribe(emps => {
      this.emprendimientos = emps
    })
  }


}
