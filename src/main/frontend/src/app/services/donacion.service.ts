import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StatusDonacionModel} from "../models/status-donacion.model";

@Injectable({
  providedIn: 'root'
})
export class DonacionService {

  constructor(
    private http: HttpClient
  ) { }

  getDonacionByExternalReference(externalReference: string) {
    return this.http.get<StatusDonacionModel>(`http://localhost:8080/donaciones/status/${externalReference}`)
  }

  realizarDonacion(emprendimiento, values: any) {
    return this.http.post('http://localhost:8080/donaciones/manguitos?mp=false', {
      emprendimiento,
      donacion: values
    })
  }
}
