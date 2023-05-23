import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { BehaviorSubject, catchError, tap } from 'rxjs'
import { User } from '../models/user.model'
import { Router } from '@angular/router'
import { Emprendimiento } from '../models/emprendimiento.model'
import { MatSnackBar } from '@angular/material/snack-bar'
import { HttpHelperService } from './error.service'
import {environment} from "../../environments/environment";

interface RegisterValues {
  url: string
  mail: string
  password: string
  passwordConfirmation: string
}

interface LoginResponse {
  mail: string,
  accessToken: string,
  expiresIn: string,
  emprendimientoUrl: string
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  user = new BehaviorSubject<User | null>(null)
  private tokenExpirationTimer: any

  constructor(
    private http: HttpClient,
    private router: Router,
    private _snackbar: MatSnackBar,
    private errorService: HttpHelperService
  ) {
  }

  login(mail: string, password: string) {
    return this.http
      .post<LoginResponse>(
        environment.apiUrl + '/login',
        { mail, password },
        httpOptions
      )
      .pipe(
        catchError(this.errorService.handleError('Log in', null)),
        tap(data => this.handleAuthentication(data))
      )
  }

  preregister({ url, mail, password, passwordConfirmation }: RegisterValues) {
    return this.http.post(environment.apiUrl + '/pre-register', {
      mail, password, passwordConfirmation, emprendimiento: { url }
    }, {responseType: 'text'})
      .pipe(
        catchError(this.errorService.handleError('Pre register', null)),
      )
  }

  register(values: { mail: string, password: string, passwordConfirmation: string, emprendimiento: Emprendimiento }) {
    return this.http.post(environment.apiUrl + '/register', values, { responseType: 'text' })
      .pipe(
        catchError(this.errorService.handleError('Register', null)),
      )
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
    const userData: { mail: string, emprendimientoUrl: string, _token: string, _expirationDate: string } = JSON.parse(rawUserData)
    const loadedUser = new User(userData.mail, userData.emprendimientoUrl, userData._token, new Date(userData._expirationDate))

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
    if (data) {
      const expirationDate = new Date(new Date().getTime() + +data.expiresIn)
      const newUser = new User(data.mail, data.emprendimientoUrl, data.accessToken, expirationDate)
      this.user.next(newUser)
      this.autoLogout(+data.expiresIn)
      localStorage.setItem('ud', JSON.stringify(newUser))
    }
  }
}
