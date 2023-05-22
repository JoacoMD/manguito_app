import { Component, Input } from '@angular/core';
import { Emprendimiento } from 'src/app/models/emprendimiento.model';

@Component({
  selector: 'app-card-emprendimiento',
  templateUrl: './card-emprendimiento.component.html',
  styleUrls: ['./card-emprendimiento.component.scss']
})
export class CardEmprendimientoComponent {

  @Input() emprendimiento: Emprendimiento
}
