import {Component, OnInit} from '@angular/core';
import {Emprendimiento} from "../models/emprendimiento.model";
import {PerfilService} from "../services/perfil.service";

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit{

  constructor(
    private perfilService: PerfilService
  ) {}

  public emprendimiento: Emprendimiento | null = null;
  loading = false

  ngOnInit(): void {
    this.loading = true
    this.perfilService.getEmprendimientoPropio().subscribe(() => {
      this.loading = false
    })
  }



}
