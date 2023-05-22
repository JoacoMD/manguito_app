import { Component } from '@angular/core';
import { Emprendimiento } from 'src/app/models/emprendimiento.model';
import { EmprendimientoService } from 'src/app/services/emprendimiento.service';

@Component({
  selector: 'app-emprendimientos-mas-donados',
  templateUrl: './emprendimientos-mas-donados.component.html',
  styleUrls: ['./emprendimientos-mas-donados.component.scss']
})
export class EmprendimientosMasDonadosComponent {

  emprendimientos: Emprendimiento[] = []

  constructor(
    private empService: EmprendimientoService
  ) {
    this.empService.getEmprendimientosMasDonados().subscribe(emps => {
      this.emprendimientos = emps
    })
  }
}
