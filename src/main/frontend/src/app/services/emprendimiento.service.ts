import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Emprendimiento} from "../models/emprendimiento.model";
import {Page} from "../models/page.model";

@Injectable({providedIn: 'root'})
export class EmprendimientoService {

  constructor(private http: HttpClient) {
  }

  searchEmprendimientos(filter: {nombre: string, categorias: string[], page: number}) {
    return this.http.get<Page<Emprendimiento>>('http://localhost:8080/emprendimientos/search', {
      params: filter
    })
  }

  getEmprendimiento(url: string) {
    return this.http.get<Emprendimiento>('http://localhost:8080/emprendimientos/' + url)
  }

  getEmprendimientos() {
    return this.http.get<Emprendimiento[]>('http://localhost:8080/emprendimientos')
  }

  getTopDonadoresEmprendimiento(url: string) {
    return this.http.get<{nombre: string, cantidadManguitos: number}[]>(`http://localhost:8080/emprendimientos/${url}/top-donadores`)
  }

}
