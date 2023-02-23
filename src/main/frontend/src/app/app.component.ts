import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { User } from './models/user.model';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

  user: User | null = null

  constructor(
    private authService: AuthService,
    private router: Router
    ) {}

  ngOnInit(): void {
    this.authService.user.subscribe(user => this.user = user)
    this.authService.autoLogin()
  }

  logout() {
    this.authService.logout();
  }

  title = 'manguito-frontend';
}
