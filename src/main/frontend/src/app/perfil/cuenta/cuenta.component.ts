import {Component, OnInit, ViewChild} from '@angular/core';
import {PerfilService} from "../../services/perfil.service";
import {NgForm} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";
import {Imagen} from "../../models/Imagen";

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

  constructor(
    public perfilService: PerfilService,
    private _sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.perfilService.emprendimiento.subscribe(emprendimiento => {
      if (emprendimiento) {
        setTimeout(() => {
          this.banner = new Imagen(emprendimiento.banner?.archivo, emprendimiento.banner?.extension)
          this.bannerLoaded = true
          this.imagenPerfil = emprendimiento.imagenPerfil ? new Imagen(emprendimiento.imagenPerfil?.archivo, emprendimiento.imagenPerfil?.extension) : new Imagen('', '')
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
    if (form.valid || this.bannerUpdated || this.imagenPerfilUpdated) {
      let datos = form.value
      if (this.bannerUpdated) {
        datos.banner = {archivo: this.banner.src, extension: this.banner.extension}
      }
      if (this.imagenPerfilUpdated) {
        datos.imagenPerfil = {archivo: this.imagenPerfil.src, extension: this.imagenPerfil.extension}
      }
      this.perfilService.actualizarDatos({...datos, categorias: this.categorias}).subscribe()
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

}
