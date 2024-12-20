import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { AlumnosComponent } from './components/views/alumnos/alumnos.component';
import { AuthGuard } from './components/helpers/auth.guard';
import { HomeComponent } from './components/views/home/home.component';
import { MapaComponent } from './components/views/mapa/mapa.component';
import { EmergenciaComponent } from './components/views/emergencia/emergencia.component';
import { PageComponent } from './components/page/page.component';

const routes: Routes = [
  { path: 'publicaciones', component: AlumnosComponent, canActivate: [AuthGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'mapa', component: PageComponent, canActivate: [AuthGuard] },
  { path: 'emergencia', component: EmergenciaComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
