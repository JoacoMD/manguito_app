import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({ providedIn: 'root' })
export class CategoriaService {

  constructor(
    private http: HttpClient
  ) {}

  fetchCategorias() {
    return this.http.get<string[]>(environment.apiUrl + '/categorias')
  }

  updateCategorias(addedCategorias: string[], removedCategorias: string[]) {
    return this.http.post(environment.apiUrl + '/categorias',
        {addedCategorias, removedCategorias},
        {responseType: 'text'}
        )
  }
}
