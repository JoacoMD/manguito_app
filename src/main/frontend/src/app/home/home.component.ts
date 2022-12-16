import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  public url: string = ""

  constructor(private router: Router) {
  }

  goToRegister() {
    this.router.navigate(['/register'], {queryParams: { url: this.url }})
  }
}
