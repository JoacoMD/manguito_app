import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { finalize, map } from 'rxjs'
import { NgForm } from '@angular/forms'
import { AuthService } from '../../services/auth.service'

@Component({
  selector: 'app-complete-register',
  templateUrl: './complete-register.component.html',
  styleUrls: ['./complete-register.component.scss']
})
export class CompleteRegisterComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
  ) {
  }


  userValues: any

  pfpSrc: string = ''
  pfpLoaded: boolean = false

  bannerSrc: string = ''
  bannerLoaded: boolean = false

  categorias: string[] = []

  loading = false

  ngOnInit() {
    this.activatedRoute.paramMap
      .pipe(map(() => window.history.state)).subscribe(res => {
      if (!res) this.router.navigate(['/register'])
      this.userValues = res
    })
  }

  onRegister(form: NgForm) {
    let emprendimiento = {
      ...form.value,
      url: this.userValues.url,
      categorias: this.categorias
    }
    const { mail, password, passwordConfirmation } = this.userValues
    this.loading = true
    this.authService.register({
      mail, password, passwordConfirmation, emprendimiento
    })
      .pipe(finalize(() => this.loading = false))
      .subscribe((res) => {
      if (res) {
        this.router.navigate(['/login'])
      }
    })
  }

  onBannerSelected(event: any) {
    const file: File = event.target.files[0]
    if (file) {
      this.bannerLoaded = false
      this.encodeImage(file, (src: string) => {
        this.bannerSrc = src
        this.bannerLoaded = true
      })
    }
  }

  onPfpSelected(event: any) {
    const file: File = event.target.files[0]
    if (file) {
      this.pfpLoaded = false
      this.encodeImage(file, (src: string) => {
        this.pfpSrc = src
        this.pfpLoaded = true
      })
    }
  }

  private encodeImage(file: File, callback: Function) {
    const pattern = /image-*/
    const reader = new FileReader()

    if (!file.type.match(pattern)) {
      alert('invalid format')
      return
    }

    reader.readAsDataURL(file)
    reader.onload = (e) => {
      // @ts-ignore
      callback(e.target.result)
    }
  }

  setCategories(event: string[]) {
    this.categorias = event
  }

}
