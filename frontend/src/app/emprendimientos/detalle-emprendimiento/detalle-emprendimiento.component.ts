import {Component, OnInit, ViewChild} from '@angular/core';
import {Emprendimiento} from "../../models/emprendimiento.model";
import {EmprendimientoService} from "../../services/emprendimiento.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Imagen} from "../../models/Imagen";
import {AbstractControl, NgForm} from "@angular/forms";
import {DonacionService} from "../../services/donacion.service";
import {finalize} from "rxjs";

@Component({
  selector: 'app-detalle-emprendimiento',
  templateUrl: './detalle-emprendimiento.component.html',
  styleUrls: ['./detalle-emprendimiento.component.css']
})
export class DetalleEmprendimientoComponent implements OnInit {

  constructor(
    private empService: EmprendimientoService,
    private route: ActivatedRoute,
    private router: Router,
    private donacionService: DonacionService
  ) {
  }

  @ViewChild("donacionForm") donacionForm: NgForm

  emprendimiento: Emprendimiento
  banner: Imagen
  imagenPerfil: Imagen
  loading: boolean = false
  topDonadores: any[] = []
  donating = false
  defaultDonacion = {
    cantidad: 1,
    nombre: "",
    contacto: "",
    mensaje: "",
  }

  ngOnInit(): void {
    this.loading = true
    this.route.params.subscribe(params => {
      this.empService.getEmprendimiento(params['url'])
        .subscribe(emp => {
          this.emprendimiento = emp
          this.loading = false
          if (emp.mostrarTopDonadores) {
            this.empService.getTopDonadoresEmprendimiento(emp.url)
              .subscribe((top) => {
                this.topDonadores = top
              })
          }
          setTimeout(() => {
            this.donacionForm.setValue(this.defaultDonacion);
          })
        })
    })
  }

  getSocialMediaLogoUrl(name: string): string {
    return `assets/logos/logo-${name.toLowerCase()}.svg`
  }

  onDonarManguitos(form: NgForm) {
    if (form.invalid) {
      return;
    }
    this.donating = true
    this.donacionService.realizarDonacion(this.emprendimiento.url, form.value)
      .pipe(finalize(() => {
        this.donating = false
      }))
      .subscribe((res) => {
        if (res) {
          this.router.navigate(['donaciones','feedback'], {queryParams: {external_reference: res}})
        }
      })
  }

  isErrorState(control: AbstractControl) {
    const isSubmitted = this.donacionForm && this.donacionForm.submitted
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted))
  }

}
