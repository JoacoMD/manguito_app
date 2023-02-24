import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar'

@Injectable({
  providedIn: 'root'
})
export class HttpHelperService {

  constructor(
    private _snackbar: MatSnackBar
  ) { }

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
        this._snackbar.open(error, 'Aceptar', {
          panelClass: ['bg-yellow-800']
        });
      else
        this._snackbar.open(error, 'Aceptar', {
          panelClass: ['bg-red-300']
        });
      return of(result as T);
    };
  }
}
