import {Component, Input} from '@angular/core';
import {StatusDonacionModel} from "../../models/status-donacion.model";

@Component({
  selector: 'app-success-donacion',
  templateUrl: './success-donacion.component.html',
  styleUrls: ['./success-donacion.component.scss']
})
export class SuccessDonacionComponent {

  @Input() donacion: StatusDonacionModel;

}
