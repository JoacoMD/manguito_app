import { Component, OnInit, ViewChild } from '@angular/core'
import { PerfilService } from '../../services/perfil.service'
import { NgForm } from '@angular/forms'
import { DomSanitizer } from '@angular/platform-browser'
import { Imagen } from '../../models/Imagen'
import { finalize } from 'rxjs'
import { MatSnackBar } from '@angular/material/snack-bar'

@Component({
  selector: 'app-cuenta',
  templateUrl: './cuenta.component.html',
  styleUrls: ['./cuenta.component.css']
})
export class CuentaComponent implements OnInit {

  @ViewChild("cuentaForm") cuentaForm: NgForm

  banner: Imagen
  imagenPerfil: Imagen

  bannerLoaded: boolean = false
  bannerUpdated: boolean = false

  imagenPerfilLoaded: boolean = false
  imagenPerfilUpdated: boolean = false

  categorias: string[] = []
  categoriasUpdated: boolean = false

  updating = false

  constructor(
    public perfilService: PerfilService,
    private _sanitizer: DomSanitizer,
    private _snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.perfilService.emprendimiento.subscribe(emprendimiento => {
      if (emprendimiento) {
        setTimeout(() => {
          this.banner = emprendimiento.banner
          this.bannerLoaded = true
          this.imagenPerfil = emprendimiento.imagenPerfil
          this.imagenPerfilLoaded = true
          this.categorias = emprendimiento.categorias
          this.cuentaForm.setValue({
            nombreEmprendimiento: emprendimiento.nombreEmprendimiento,
            url: emprendimiento.url,
            descripcion: emprendimiento.descripcion,
            mostrarTopDonadores: emprendimiento.mostrarTopDonadores,
            ocultarManguitosRecibidos: emprendimiento.ocultarManguitosRecibidos
          })
        }, 100)
      }
    })
  }

  updateCuenta(form: NgForm) {
    if (form.valid || this.bannerUpdated || this.imagenPerfilUpdated || this.categoriasUpdated) {
      let datos = form.value
      if (this.bannerUpdated) {
        datos.newBanner = {archivo: this.banner.rawSrc, type: this.banner.type}
      }
      if (this.imagenPerfilUpdated) {
        datos.newImagenPerfil = {archivo: this.imagenPerfil.rawSrc, type: this.imagenPerfil.type}
      }
      this.updating = true
      this.perfilService.actualizarDatos({...datos, categorias: this.categorias})
        .pipe(
          finalize(() => this.updating = false)
        )
        .subscribe((res) => {
          if (res) {
            this.bannerUpdated = false
            this.imagenPerfilUpdated = false
            this.categoriasUpdated = false
            this._snackbar.open('Datos actualizados correctamente')
          }
        })
    }
  }

  onFileSelected(event: any) {
    const file:File = event.target.files[0];

    if (file) {
      const pattern = /image-*/;
      const reader = new FileReader();

      if (!file.type.match(pattern)) {
        alert('invalid format');
        return;
      }

      reader.readAsDataURL(file);
      reader.onload = (e) => {
        // @ts-ignore
        const src= e.target.result.replace(/^data:image\/[a-z]+;base64,/, "");
        this.banner = new Imagen(src, file.type)
        this.bannerUpdated = true;
        this.bannerLoaded = true;
      };
    }
  }

  onPfpSelected(event: any) {
    const file:File = event.target.files[0];

    if (file) {
      const pattern = /image-*/;
      const reader = new FileReader();

      if (!file.type.match(pattern)) {
        alert('invalid format');
        return;
      }

      reader.readAsDataURL(file);
      reader.onload = (e) => {
        // @ts-ignore
        const src= e.target.result.replace(/^data:image\/[a-z]+;base64,/, "");
        this.imagenPerfil = new Imagen(src, file.type)
        this.imagenPerfilUpdated = true;
        this.imagenPerfilLoaded = true;
      };
    }
  }

  onChangeCategorias(cats) {
    this.categorias = cats
    this.categoriasUpdated = true
  }

  isDirty() {
    const other = this.bannerUpdated || this.imagenPerfilUpdated || this.categoriasUpdated
    return this.cuentaForm?.dirty || other
  }

  getSrc(imagen: any) {
    return typeof imagen === 'string' ? imagen : imagen.src
  }

}
