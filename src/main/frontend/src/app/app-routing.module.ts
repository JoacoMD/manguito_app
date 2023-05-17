import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { HomeComponent } from './home/home.component'
import { LoginComponent } from './login/login.component'
import { RegisterComponent } from './register/register.component'
import { PerfilComponent } from './perfil/perfil.component'
import { CuentaComponent } from './perfil/cuenta/cuenta.component'
import { RedesSocialesComponent } from './perfil/redes-sociales/redes-sociales.component'
import { PagosComponent } from './perfil/pagos/pagos.component'
import { OtrosComponent } from './perfil/otros/otros.component'
import { EmprendimientosComponent } from './emprendimientos/emprendimientos.component'
import {
  DetalleEmprendimientoComponent
} from './emprendimientos/detalle-emprendimiento/detalle-emprendimiento.component'
import {AuthGuard} from "./auth/auth.guard";
import {CompleteRegisterComponent} from "./register/complete-register/complete-register.component";
import {AdminComponent} from "./admin/admin.component";
import {CategoriasComponent} from "./admin/categorias/categorias.component";
import {AdminGuard} from "./auth/admin.guard";
import {LoggedGuard} from "./auth/logged.guard";
import {FeedbackDonacionComponent} from "./donaciones/feedback-donacion/feedback-donacion.component";

const routes: Routes = [
  { path: 'login', canActivate: [LoggedGuard], component: LoginComponent },
  { path: 'register', canActivate: [LoggedGuard], component: RegisterComponent },
  { path: 'complete-register', component: CompleteRegisterComponent },
  {
    path: 'perfil', canActivate: [AuthGuard], component: PerfilComponent, children: [
      { path: '', pathMatch: 'full', redirectTo: 'cuenta'},
      { path: 'cuenta', pathMatch: 'full', component: CuentaComponent},
      { path: 'redes-sociales', component: RedesSocialesComponent},
      { path: 'pagos', component: PagosComponent},
      { path: 'otros', component: OtrosComponent},
    ]
  },
  { path: 'admin', canActivate: [AdminGuard], component: AdminComponent, children: [
      {path: '', pathMatch: 'full', redirectTo: 'categorias'},
      {path: 'categorias',component: CategoriasComponent}
    ]
  },
  { path: 'emprendimientos', component: EmprendimientosComponent},
  { path: 'emprendimientos/:url', component: DetalleEmprendimientoComponent},
  { path: 'donaciones/feedback', component: FeedbackDonacionComponent},
  { path: '', pathMatch: 'full', component: HomeComponent },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
