import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Emprendimiento} from "../models/emprendimiento.model";
import {Page} from "../models/page.model";
import {environment} from "../../environments/environment";

@Injectable({providedIn: 'root'})
export class EmprendimientoService {

  constructor(private http: HttpClient) {
  }

  searchEmprendimientos(filter: {nombre: string, categorias: string[], page: number}) {
    return this.http.get<Page<Emprendimiento>>(environment.apiUrl + '/emprendimientos/search', {
      params: filter
    })
  }

  getEmprendimiento(url: string) {
    return this.http.get<Emprendimiento>(environment.apiUrl + '/emprendimientos/' + url)
  }

  getEmprendimientos() {
    return this.http.get<Emprendimiento[]>(environment.apiUrl + '/emprendimientos')
  }

  getTopDonadoresEmprendimiento(url: string) {
    return this.http.get<{nombre: string, cantidadManguitos: number}[]>(`${environment.apiUrl}/emprendimientos/${url}/top-donadores`)
  }

}
