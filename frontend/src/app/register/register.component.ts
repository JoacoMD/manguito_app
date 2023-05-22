import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from "../services/auth.service";
import { finalize } from 'rxjs'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit{

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.url = params['url'] || '';
    })
  }

  public url = ''
  loading = false

  onRegister(form: NgForm) {
    if (form.valid) {
      this.loading = true
      this.authService.preregister(form.value)
        .pipe(finalize(() => this.loading = false))
        .subscribe((res) => {
        if (res) {
          this.router.navigate(['/complete-register'], {state: form.value})
        }
      })
    } else {
      form.form.markAllAsTouched()
    }
  }
}
