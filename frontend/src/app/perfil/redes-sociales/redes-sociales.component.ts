import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {AbstractControl, NgForm} from "@angular/forms";
import {
  FACEBOOK_USER_URL_PATTERN,
  INSTAGRAM_USER_URL_PATTERN, TWITCH_USER_URL_PATTERN,
  TWITTER_USER_URL_PATTERN,
  YOUTUBE_CHANNEL_URL_PATTERN
} from "../../const/patterns";
import {PerfilService} from "../../services/perfil.service";
import {finalize} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-redes-sociales',
  templateUrl: './redes-sociales.component.html',
  styleUrls: ['./redes-sociales.component.css']
})
export class RedesSocialesComponent implements AfterViewInit {

  @ViewChild("redesSocialesForm") redesSocialesForm: NgForm

  twitterUrlPattern = TWITTER_USER_URL_PATTERN
  facebookUrlPattern = FACEBOOK_USER_URL_PATTERN
  youtubeUrlPattern = YOUTUBE_CHANNEL_URL_PATTERN
  instagramUrlPattern = INSTAGRAM_USER_URL_PATTERN
  twitchUrlPattern = TWITCH_USER_URL_PATTERN

  updating = false

  constructor(
    private perfilService: PerfilService,
    private _snackbar: MatSnackBar
  ) {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.perfilService.emprendimiento.subscribe(emprendimiento => {
        if (emprendimiento) {
          let initialValue = {
            'twitter': '',
            'facebook': '',
            'youtube': '',
            'instagram': '',
            'twitch': ''
          }
          this.redesSocialesForm.setValue(emprendimiento.redesSociales.reduce((obj, item) => {
            obj[item.redSocial.toLowerCase()] = item.url
            return obj
          }, initialValue))
        }
      })
    })
  }

  onSubmit(form: NgForm) {
    if (form.invalid) {
      return
    }
    const values = Object.keys(form.value).reduce((acc, key) => {
      if (form.value[key] || form.value[key] === "" && form.controls[key].dirty) {
        acc.push({url: form.value[key], redSocial: key})
      }
      return acc
    }, [])
    this.updating = true
    this.perfilService.actualizarDatos({redesSociales: values})
      .pipe(finalize(() => {
        this.updating = false
      }))
      .subscribe((res) => {
        if (res) {
          this._snackbar.open("Datos actualizados correctamente", null, {duration: 2000})
        }
      })
  }

  isErrorState(control: AbstractControl) {
    const isSubmitted = this.redesSocialesForm && this.redesSocialesForm.submitted
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted))
  }

}
