import { Component, OnInit } from '@angular/core';

import { Form, FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlumnosService } from 'src/app/services/alumnos.service';
import { LoginService } from 'src/app/services/login.service';

import Swal from 'sweetalert2';
declare var bootstrap: any;
@Component({
  selector: 'app-emergencia',
  templateUrl: './emergencia.component.html',
  styleUrls: ['./emergencia.component.css']
}
)

export class EmergenciaComponent implements OnInit {
    listaAlumnos: any[] = []
    listChats: any[] = []
    listarDistritos: any[] = []
    FormEmergencia: FormGroup
    formChat:FormGroup
    title: any
    nameBoton: any
    id: number
    idActual: number
    idDistritoCom:number = 0
    emer:string = "si"
    

  constructor(private _alumnoService:AlumnosService,
      private _loginService: LoginService,
      private route: Router) { }

  ngOnInit(): void {
    this.initForm()
    this.obtenerDistritos()
    console.log("Componente cargado");
  }

  initForm() {
      this.FormEmergencia = new FormGroup({
        descripcion:new FormControl(null,[Validators.required]),
        img:new FormControl("A"),
        direccion:new FormControl(null,[Validators.required]),
        estado: new FormControl("A"),
        idDistrito: new FormControl(null, [Validators.required]),
        emergencia: new FormControl(this.emer),
        AllChatByPublic:new FormArray([])
      });
    }

    registrarAlumno(formulario: any): void {
      if (this.FormEmergencia.valid) {
        console.log('Datos enviados al servicio:', formulario);
    
        this._alumnoService.generarAlumnos(formulario)
        .subscribe(response => {
          this.cerrarModal();
          this.resetForm();
          console.log('Emergencia registrado', response);
        }, error => {
          console.error('Error al registrar Emergencia', error);
        });
      } else {
        console.log('Formulario no válido:', this.FormEmergencia);
      }
    }
    

    cerrarModal() {
      const modalElement = document.getElementById('modalEmergencia');
      const modal = bootstrap.Modal.getInstance(modalElement);
      modal.hide();
    }
    resetForm(): void {
      this.FormEmergencia.reset();
      this.FormEmergencia.controls['estado'].setValue('A')
      this.FormEmergencia.controls['img'].setValue('A')
      this.FormEmergencia.controls['emergencia'].setValue(this.emer)
    }
    cerrarBoton() {
      this.resetForm()
      this.cerrarModal()
    }

    crearEditarAlumno(boton: any) {
      if (boton == "Guardar") {
        this.FormEmergencia.controls['estado'].setValue('A')
        this.alertRegistro()
      } else {
        console.log("waa")
      }
    }

    alertRegistro() {
      if (this.FormEmergencia.valid) {
        Swal.fire({
          title: '¿Estás seguro de registrar el Emergencia?',
          icon: 'success',
          showCancelButton: true,
          confirmButtonText: 'Sí, confirmar',
          cancelButtonText: 'Cancelar'
        }).then((result) => {
          if (result.isConfirmed) {
            this.registrarAlumno(this.FormEmergencia.value)
            this.alertaExitosa("registrado")
          }
        });
      }else{
        this.validarFormulario();
      }
    
    }

    alertaExitosa(titulo: any){
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: "Emergencia "+titulo,
        showConfirmButton: false,
        timer: 1500
      });
    }

    validarFormulario() {
      Object.keys(this.FormEmergencia.controls).forEach(key => {
        this.FormEmergencia.markAllAsTouched();
        const control = this.FormEmergencia.get(key);
    
        if (control && control.invalid) {
          console.log(`Errores en el campo ${key}: `, control.errors);
        }
      });
    }

    titulo(titulo: any, id: number) {
      this.title = `${titulo} Emergencia`
      titulo == "Crear" ? this.nameBoton = "Guardar" : this.nameBoton = "Modificar"
    }

    obtenerDistritos(){
      this._alumnoService.listarDistritos()
      .subscribe((data)=>{
        this.listarDistritos = data.distritos; // -> este dato "distritos" es el json que mandas en el listarDistritos
        if(this.listarDistritos.length == 0){
          console.log("No hay datos")
        }
      });
    }

   /* obtenerUbicacion() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;
            console.log("Ubicación obtenida: ", latitude, longitude);
  
            // Puedes asignar la ubicación a un campo o mostrarla en un input
            // Por ejemplo, asignando al campo 'direccion':
            const ubicacion = `Lat: ${latitude}, Long: ${longitude}`;
            console.log(ubicacion);
            // Si necesitas actualizar la dirección en un campo del formulario, puedes hacer algo como:
            // this.FormEmergencia.controls['direccion'].setValue(ubicacion);
          },
          (error) => {
            console.error("Error al obtener la ubicación: ", error);
            alert("No se pudo obtener la ubicación.");
          }
        );
      } else {
        alert("La geolocalización no es compatible con este navegador.");
      }
    }*/
}
