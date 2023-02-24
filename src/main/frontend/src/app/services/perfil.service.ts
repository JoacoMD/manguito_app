import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Emprendimiento} from "../models/emprendimiento.model";
import {BehaviorSubject, exhaustMap, take, tap} from "rxjs";

@Injectable({providedIn: 'root'})
export class PerfilService {

  emprendimiento = new BehaviorSubject<Emprendimiento | null>(null)
  private url: string

  constructor(private http: HttpClient) {
  }

  getEmprendimientoPropio() {
    return this.http.get<Emprendimiento>('http://localhost:8080/emprendimientos/actual')
      .pipe(tap((emprendimiento) => {
        this.emprendimiento.next(emprendimiento)
      }))
  }

  actualizarDatos(emprendimiento: any) {
    return this.emprendimiento.pipe(take(1), exhaustMap(emp => {
      return this.http.put(`http://localhost:8080/emprendimientos/${emp?.url}`, emprendimiento)
    }))
  }
}
