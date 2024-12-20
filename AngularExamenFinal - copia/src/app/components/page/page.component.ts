import { Component, OnInit } from '@angular/core';
import { UbicacionesService } from 'src/app/services/ubicaciones.service';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css'],
})
export class PageComponent implements OnInit {
  distritos: { id: number; descripcion: string }[] = []; // Array para los distritos
  distritoSeleccionado: string = ""; // Variable para almacenar el nombre del distrito seleccionado
  nombreDistritoSeleccionado: string = ""; // Nombre del distrito seleccionado
  tipoSeleccionado: string = ''; // Tipo: bomberos, hospitales o comisarias
  ubicaciones: any[] = []; // Datos de bomberos/comisarías/hospitales filtrados

  constructor(
    private ubicacionesService: UbicacionesService,
    private route: Router,
    private _loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.cargarDistritos();
  }

  cargarDistritos(): void {
    this.ubicacionesService.obtenerDistritos().subscribe({
      next: (data) => {
        this.distritos = data; // Asignamos los distritos al arreglo
      },
      error: (err) => {
        console.error('Error al obtener los distritos:', err);
      },
    });
  }

  cargarUbicaciones(): void {
    const distrito = this.distritos.find(d => String(d.id) === this.distritoSeleccionado);
  
    if (distrito) {
      const distritoId = distrito.id; // Obtén el ID del distrito
      this.ubicacionesService.obtenerPorTipoYDistrito(this.tipoSeleccionado, distritoId)
        .subscribe({
          next: (data) => {
            // Asignamos los datos tal como vienen del backend
            this.ubicaciones = data;
          },
          error: (err) => {
            console.error('Error al obtener ubicaciones:', err);
          },
        });
    }
  }
  
  
  logout(){
    Swal.fire({
      title: '¿Estás seguro de cerrar sesion?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this._loginService.logout()
        this.route.navigate(['login'])
      }
    });
  
  }
  
  onFiltroCambiado(): void {
    // Encuentra el distrito seleccionado en el array `distritos`
    const distrito = this.distritos.find(d => String(d.id) === this.distritoSeleccionado);
  
    // Asigna el nombre del distrito o un valor por defecto si no se encuentra
    this.nombreDistritoSeleccionado = distrito ? distrito.descripcion : 'Distrito desconocido';
  
    // Carga las ubicaciones
    this.cargarUbicaciones();
  }

}


