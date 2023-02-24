import {Component, OnInit} from '@angular/core';
import {Emprendimiento} from "../models/emprendimiento.model";
import {EmprendimientoService} from "../services/emprendimiento.service";

@Component({
  selector: 'app-emprendimientos',
  templateUrl: './emprendimientos.component.html',
  styleUrls: ['./emprendimientos.component.css']
})
export class EmprendimientosComponent implements OnInit {

  constructor(
    private emprendimientoService: EmprendimientoService
  ) {}

  loading: boolean = false

  emprendimientos: Emprendimiento[] = []

  ngOnInit() {
    this.loading = true
    this.emprendimientoService.getEmprendimientos().subscribe(emps => {
      this.emprendimientos = emps
      this.loading = false
    })
  }

}
