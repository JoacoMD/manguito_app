import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  constructor(private authService: AuthService, private router: Router) {}

  onLogin(loginForm: NgForm) {
    if (loginForm.valid) {
      this.authService
        .login(loginForm.value.mail, loginForm.value.password)
        .subscribe(() => {
          this.router.navigate(['/']);
        });
    }
  }
}
