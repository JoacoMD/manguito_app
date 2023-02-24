import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({ providedIn: 'root' })
export class CategoriaService {

  constructor(
    private http: HttpClient
  ) {}

  fetchCategorias() {
    return this.http.get<string[]>('http://localhost:8080/categorias')
  }

  updateCategorias(addedCategorias: string[], removedCategorias: string[]) {
    return this.http.post('http://localhost:8080/categorias', {addedCategorias, removedCategorias})
  }
}
