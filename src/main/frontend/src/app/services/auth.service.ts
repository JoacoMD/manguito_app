import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, tap } from 'rxjs';
import { User } from './user.model';

interface RegisterValues {
  url: string
  mail: string
  password: string
  passwordConfirmation: string
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};
@Injectable()
export class AuthService {
  user = new Subject<User>();

  constructor(private http: HttpClient) {}

  /** GET heroes from the server */
  login(mail: string, password: string) {
    return this.http
      .post<User>(
        'http://localhost:8080/login',
        { mail, password },
        httpOptions
      )
      .pipe(tap((res) => this.user.next(res)));
  }

  register({url, mail, password, passwordConfirmation}: RegisterValues) {
    return this.http.post('http://localhost:8080/register', {
      mail, password, passwordConfirmation, emprendimiento: { url }
    }, {responseType: 'text'})
  }
}
