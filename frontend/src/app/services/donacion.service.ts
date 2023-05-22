import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StatusDonacionModel} from "../models/status-donacion.model";
import {DonacionManguito, ResponseDonaciones, Suscripcion} from "../models/donacion.model";
import {PerfilService} from "./perfil.service";
import {BehaviorSubject, finalize, Subject, switchMap, tap} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DonacionService {

  constructor(
    private http: HttpClient,
    private perfilService: PerfilService
  ) { }

  manguitosRecibidos = new BehaviorSubject<DonacionManguito[] | null>(null)
  suscripcionesPropias = new BehaviorSubject<Suscripcion[] | null>(null)
  loadingDonaciones = new BehaviorSubject<boolean>(false)

  getDonacionByExternalReference(externalReference: string) {
    return this.http.get<StatusDonacionModel>(`${environment.apiUrl}/donaciones/status/${externalReference}`)
  }

  realizarDonacion(emprendimiento, values: any) {
    return this.http.post(environment.apiUrl + '/donaciones/manguitos?mp=false', {
      emprendimiento,
      donacion: values
    })
  }

  getDonacionesByEmprendimientoPropio() {
    this.loadingDonaciones.next(true)
    this.perfilService.getEmprendimientoPropio().pipe(
      switchMap((emp) => {
        if (!emp) return null
        return this.http.get<ResponseDonaciones>(`${environment.apiUrl}/emprendimientos/${emp.url}/donaciones`)
      }),
      tap((donaciones) => {
        this.manguitosRecibidos.next(donaciones.manguitos)
        this.suscripcionesPropias.next(donaciones.suscripciones)
      }),
      finalize(() => this.loadingDonaciones.next(false))
    ).subscribe()
  }
}
