import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {User} from "../models/user.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public url: string = ""
  user: User | null

  constructor(
    private router: Router,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.authService.user.subscribe(user => this.user = user)
  }

  goToRegister() {
    this.router.navigate(['/register'], {queryParams: { url: this.url }})
  }
}
