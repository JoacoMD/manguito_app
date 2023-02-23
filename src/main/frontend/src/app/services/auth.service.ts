import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, tap} from 'rxjs';
import { User } from '../models/user.model';
import {Router} from "@angular/router";

interface RegisterValues {
  url: string
  mail: string
  password: string
  passwordConfirmation: string
}

interface LoginResponse {
  mail: string,
  accessToken: string,
  expiresIn: string
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};
@Injectable({providedIn: 'root'})
export class AuthService {
  user = new BehaviorSubject<User | null>(null);
  private tokenExpirationTimer: any;

  constructor(
    private http: HttpClient,
    private router: Router
    ) {}

  /** GET heroes from the server */
  login(mail: string, password: string) {
    return this.http
      .post<LoginResponse>(
        'http://localhost:8080/login',
        { mail, password },
        httpOptions
      )
      .pipe(tap(data => this.handleAuthentication(data)));
  }

  register({url, mail, password, passwordConfirmation}: RegisterValues) {
    return this.http.post('http://localhost:8080/register', {
      mail, password, passwordConfirmation, emprendimiento: { url }
    }, {responseType: 'text'})
  }

  logout() {
    this.user.next(null)
    this.router.navigate(['/login'])
    localStorage.removeItem('ud')
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer)
    }
    this.tokenExpirationTimer = null
  }

  autoLogin() {
    const rawUserData = localStorage.getItem('ud')
    if (!rawUserData) {
      return
    }
    const userData: {mail: string, _token: string, _expirationDate: string} = JSON.parse(rawUserData)
    const loadedUser = new User(userData.mail, userData._token, new Date(userData._expirationDate))

    if (loadedUser.token) {
      this.user.next(loadedUser)
      const expirationDuration = new Date(userData._expirationDate).getTime() - new Date().getTime()
      this.autoLogout(expirationDuration)
    }
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout()
    }, expirationDuration)
  }

  private handleAuthentication(data: LoginResponse) {
    const expirationDate = new Date(new Date().getTime() + +data.expiresIn)
    const newUser = new User(data.mail, data.accessToken, expirationDate)
    this.user.next(newUser)
    this.autoLogout(+data.expiresIn)
    localStorage.setItem('ud', JSON.stringify(newUser))
  }
}
