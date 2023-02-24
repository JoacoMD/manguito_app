import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Emprendimiento} from "../models/emprendimiento.model";
import {map} from "rxjs";
import {Imagen} from "../models/Imagen";

@Injectable({providedIn: 'root'})
export class EmprendimientoService {

  constructor(private http: HttpClient) {
  }

  getEmprendimiento(url: string) {
    return this.http.get<Emprendimiento>('http://localhost:8080/emprendimientos/' + url)
  }

  getEmprendimientos() {
    return this.http.get<Emprendimiento[]>('http://localhost:8080/emprendimientos')
      .pipe(
        map(emps => emps.map(emp => ({
            ...emp,
            banner: emp.banner && new Imagen(emp.banner?.archivo, emp.banner?.extension),
            imagenPerfil: emp.imagenPerfil ? new Imagen(emp.imagenPerfil?.archivo, emp.imagenPerfil?.extension) : new Imagen('', ''),
          })
        ))
      )
  }

}
