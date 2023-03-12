import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class HttpHelperService {

  constructor(private toastr: ToastrService) {}

  public handleError<T>(operacion = 'operacion', result?: T) {
    return (response: any): Observable<T> => {
      console.error(response);
      console.error(`Fall\u00F3 la operaci\u00F3n ${ operacion }: ${ response.message }`);
      let error;
      switch (response.status) {
        case 0:
          error = 'Error al intentar conectar con el servidor';
          break;
        default:
          error = response.error;
          break;
      }
      if (response.status === 409)
        this.toastr.warning(error.message);
      else
        this.toastr.error(error.message);
      return of(result as T);
    };
  }
}
