import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {RegisterComponent} from './register/register.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthService} from './services/auth.service';
import {PerfilComponent} from './perfil/perfil.component';
import {CuentaComponent} from './perfil/cuenta/cuenta.component';
import {RedesSocialesComponent} from './perfil/redes-sociales/redes-sociales.component';
import {PagosComponent} from './perfil/pagos/pagos.component';
import {OtrosComponent} from './perfil/otros/otros.component';
import {EmprendimientosComponent} from './emprendimientos/emprendimientos.component';
import {
  DetalleEmprendimientoComponent
} from './emprendimientos/detalle-emprendimiento/detalle-emprendimiento.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatChipsModule} from "@angular/material/chips";
import {AuthInterceptorService} from "./auth/auth-interceptor.service";
import {MatMenuModule} from "@angular/material/menu";
import {CompleteRegisterComponent} from './register/complete-register/complete-register.component';
import {MatIconModule} from "@angular/material/icon";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {CategoriasAutocompleteComponent} from './utils/categorias-autocomplete/categorias-autocomplete.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {AdminComponent} from './admin/admin.component';
import {CategoriasComponent} from './admin/categorias/categorias.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {ToastrModule} from "ngx-toastr";
import {CommonModule} from "@angular/common";
import {SpinnerComponent} from "./utils/spinner.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    PerfilComponent,
    CuentaComponent,
    RedesSocialesComponent,
    PagosComponent,
    OtrosComponent,
    EmprendimientosComponent,
    DetalleEmprendimientoComponent,
    CompleteRegisterComponent,
    CategoriasAutocompleteComponent,
    AdminComponent,
    CategoriasComponent,
    SpinnerComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonToggleModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatChipsModule,
    MatMenuModule,
    MatIconModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    ToastrModule.forRoot({positionClass: 'toast-top-center'}),
  ],
  providers: [AuthService, {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
