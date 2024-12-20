import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/auth/login/login.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthInterceptor } from './components/helpers/auth.interceptor';
import { AlumnosComponent } from './components/views/alumnos/alumnos.component';
import { AuthGuard } from './components/helpers/auth.guard';
import { HomeComponent } from './components/views/home/home.component';
import { EmergenciaComponent } from './components/views/emergencia/emergencia.component';
import { MapaComponent } from './components/views/mapa/mapa.component';
import { HeaderComponent } from './components/views/layout/header/header.component';
import { ModalComponent } from './components/modal/modal.component';
import { PageComponent } from './components/page/page.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AlumnosComponent,
    HomeComponent,
    EmergenciaComponent,
    MapaComponent,
    HeaderComponent,
    ModalComponent,
    PageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule,
  ],
  providers: [
    {
      provide : HTTP_INTERCEPTORS, useClass : AuthInterceptor, multi : true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
