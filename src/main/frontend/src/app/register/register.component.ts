import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from "../services/auth.service";

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

  onRegister(form: NgForm) {
    if (form.valid) {
      this.authService.register(form.value).subscribe(() => {
        this.router.navigate(['/login'])
      })
    }
  }
}
