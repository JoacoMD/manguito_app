import { Component } from '@angular/core'
import { NgForm } from '@angular/forms'
import { Router } from '@angular/router'
import { AuthService } from '../services/auth.service'
import { finalize } from 'rxjs'
import { MatSnackBar } from '@angular/material/snack-bar'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private _snackbar: MatSnackBar
  ) {
  }

  loading = false

  onLogin(loginForm: NgForm) {
    if (loginForm.valid) {
      this.loading = true
      this.authService
        .login(loginForm.value.mail, loginForm.value.password)
        .pipe(finalize(() => this.loading = false))
        .subscribe((user) => {
          if (user) {
            this.router.navigate(['/'])
          }
        })
    } else {
      loginForm.form.markAllAsTouched()
    }
  }
}
