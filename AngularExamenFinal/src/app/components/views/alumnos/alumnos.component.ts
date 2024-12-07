import { Component, OnInit } from '@angular/core';

import { Form, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlumnosService } from 'src/app/services/alumnos.service';
import { LoginService } from 'src/app/services/login.service';
import { validEstado } from '../validacion/estado.validacion';
import Swal from 'sweetalert2';

declare var bootstrap: any;
@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})


export class AlumnosComponent implements OnInit {
  listaAlumnos: any[] = []
  formAlumno: FormGroup
  title: any
  nameBoton: any
  id: number

  constructor(
    private _alumnoService:AlumnosService,
    private _loginService: LoginService,
    private route: Router
  ) { }

  ngOnInit(): void {
    this.obtnerAlumnos()
    this.initForm()
  }

  initForm() {
    this.formAlumno = new FormGroup({
      nombre:new FormControl(null,[Validators.required]),
      apellido:new FormControl(null,[Validators.required]),
      img:new FormControl(null,[Validators.required]),
      estado: new FormControl(null, [Validators.required, validEstado()]),
    });
  }

  obtnerAlumnos(){
    this._alumnoService.listarAlumnos()
    .subscribe((data) =>{
      this.listaAlumnos = data.alumnos;
      console.log(data.alumnos)
      if(this.listaAlumnos.length==0){
        console.log("No Daots")
      }
    });
  }

 obtenerAlumnoPorId(id:any){
  let form = this.formAlumno
  this._alumnoService.obtenerAlumnos(id)
  .subscribe((data) =>{
    form.controls['descripcion'].setValue(data.alumnos.descripcion)
    form.controls['img'].setValue(data.alumnos.img)
    form.controls['direccion'].setValue(data.alumnos.direccion)
    form.controls['estado'].setValue(data.alumnos.estado)
    console.log(data.alumnos)
  });
 }


 eliminarAlumnos(id: any) {
  Swal.fire({
    title: '¿Estás seguro de eliminar el Alumno?',
    icon: 'error',
    showCancelButton: true,
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (result.isConfirmed) {

      this._alumnoService.eliminarAlumnos(id)
        .subscribe((data) => {
          console.log("Alumno eliminado", data)
          this.listaAlumnos = this.listaAlumnos.filter(item => item.id !== id);
        }, error => {
          console.error('Error al eliminar el alumno', error);
        });

        this.alertaExitosa("eliminado")

    }
  });

}
registrarAlumno(formulario: any): void {
  if (this.formAlumno.valid) {
    console.log('Datos enviados al servicio:', formulario);

    this._alumnoService.generarAlumnos(formulario)
    .subscribe(response => {
      this.cerrarModal();
      this.obtnerAlumnos();
      this.resetForm();
      console.log('Alumno registrado', response);
    }, error => {
      console.error('Error al registrar Alumno', error);
    });
  } else {
    console.log('Formulario no válido:', this.formAlumno);
  }
}

editarAlumno(id: number, formulario: any): void {
  if (this.formAlumno.valid) {
    this._alumnoService.editarAlumnos(id, formulario)
    .subscribe(response => {
      this.cerrarModal()
      this.obtnerAlumnos()
      this.resetForm()
      console.log('Alumno modificado', response);
    }, error => {
      console.error('Error al modificar alumno', error);
      this.validarFormulario();
    }
  );
  }
}

titulo(titulo: any, id: number) {
  this.title = `${titulo} alumno`
  titulo == "Crear" ? this.nameBoton = "Guardar" : this.nameBoton = "Modificar"
  if (id != null) {
    this.id = id
    this.obtenerAlumnoPorId(id)
  }
}

crearEditarAlumno(boton: any) {
  if (boton == "Guardar") {
    this.formAlumno.controls['estado'].setValue('A')
    this.alertRegistro()
  } else {
    this.alertModificar()
  }
}

cerrarModal() {
  const modalElement = document.getElementById('modalAlumno');
  const modal = bootstrap.Modal.getInstance(modalElement);
  modal.hide();
}

resetForm(): void {
  this.formAlumno.reset();
  this.formAlumno.controls['estado'].setValue('A')
}

cerrarBoton() {
  this.resetForm()
  this.cerrarModal()
}

alertRegistro() {
  if (this.formAlumno.valid) {
    Swal.fire({
      title: '¿Estás seguro de registrar el alumno?',
      icon: 'success',
      showCancelButton: true,
      confirmButtonText: 'Sí, confirmar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.registrarAlumno(this.formAlumno.value)
        this.alertaExitosa("registrado")
      }
    });
  }else{
    this.validarFormulario();
  }

}

alertModificar() {
  if (this.formAlumno.valid) {
    Swal.fire({
      title: '¿Estás seguro de modificar el alumno?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, modificar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.editarAlumno(this.id, this.formAlumno.value)
        this.alertaExitosa("modificado")
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
    title: "Alumno "+titulo,
    showConfirmButton: false,
    timer: 1500
  });
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
validarFormulario() {
  Object.keys(this.formAlumno.controls).forEach(key => {
    this.formAlumno.markAllAsTouched();
    const control = this.formAlumno.get(key);

    if (control && control.invalid) {
      console.log(`Errores en el campo ${key}: `, control.errors);
    }
  });
}

}
