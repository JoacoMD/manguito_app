import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { User } from './services/user.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  user: User | null = null

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.user.subscribe(user => this.user = user)
  }

  title = 'manguito-frontend';
}
