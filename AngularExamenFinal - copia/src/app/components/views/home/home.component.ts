import { Component, OnInit } from '@angular/core';

import { Form, FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlumnosService } from 'src/app/services/alumnos.service';
import { LoginService } from 'src/app/services/login.service';
import { validEstado } from '../validacion/estado.validacion';
import Swal from 'sweetalert2';

declare var bootstrap: any;
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  listaAlumnos: any[] = []
  listChats: any[] = []
  listarDistritos: any[] = []
  formAlumno: FormGroup
  formChat:FormGroup
  title: any
  nameBoton: any
  id: number
  idActual: number
  idDistritoCom:number = 0
  emer:string = "si"


  constructor(
    private _alumnoService:AlumnosService,
    private _loginService: LoginService,
    private route: Router
  ){}

  ngOnInit(): void {
    //this.obtnerAlumnos()
    this.obtenerPublicacionesPorIdDistrito(this.idDistritoCom)
    this.initForm()
    this.initForm2()
    this.obtenerIdActual()
   // this.setearIdDistrito(this.idDistrito)
    this.obtenerPublicacionesPorIdDistrito(this.idDistritoCom)
    this.obtenerDistritos()
    setInterval(() => this.cargarMensajes(this.chatId),1000);
  }

initForm() {
    this.formAlumno = new FormGroup({
      descripcion:new FormControl(null,[Validators.required]),
      img:new FormControl("A"),
      direccion:new FormControl(null,[Validators.required]),
      estado: new FormControl("A"),
      idDistrito: new FormControl(null, [Validators.required]),
      emergencia: new FormControl(this.emer),
      AllChatByPublic:new FormArray([])
    });
  }
  initForm2() {
    this.formChat = new FormGroup({
      mensaje:new FormControl(null,[Validators.required])
    });
  }

  /*obtnerAlumnos(){
    this._alumnoService.listarAlumnos()
    .subscribe((data) =>{
      this.listaAlumnos = data.alumnos;
      console.log(data.alumnos)
    });
  }*/
  obtenerIdActual(){
    this._alumnoService.usuarioActual()
    .subscribe((data) =>{
      this.idActual = data.idActualUsuario
      console.log("IdUsuario Actual",this.idActual)
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
    form.controls['idDistrito'].setValue(data.alumnos.idDistrito)
    console.log(data.alumnos)
  });
 }
 /*setearIdDistrito(idDistrito){
  idDistrito = 0
  console.log(idDistrito ,"se seteo exitosamente")
 }*/
 obtenerIdDistritoDeComboBox(event:any):void{
  this.idDistritoCom = +event.target.value;
  this.obtenerPublicacionesPorIdDistrito(this.idDistritoCom)
  console.log("Distrito seleccionado",this.idDistritoCom)
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
SelecionarPublicacionPorDistritoComboBox(idDistrito){
  this.idDistritoCom = +event.target.addEventListener;  // Convierte el valor a número
  console.log('Distrito seleccionado:', this.idDistritoCom);

  
}

 obtenerPublicacionesPorIdDistrito(idDistrito){
  this._alumnoService.listarpublicacionesPorIdDistrito(this.idDistritoCom,this.emer)
  .subscribe((data)=>{
    this.listaAlumnos = data.alumnos; // -> este dato "alumno" es el json que mandas en el listarDistritos
    if(this.listarDistritos.length == 0){
      console.log("No hay datos")
    }
  });
 }


 eliminarAlumnos(id: any) {
  Swal.fire({
    title: '¿Estás seguro de eliminar el Emergencia?',
    icon: 'error',
    showCancelButton: true,
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (result.isConfirmed) {

      this._alumnoService.eliminarAlumnos(id)
        .subscribe((data) => {
          console.log("Emergencia eliminado", data)
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
      this.obtenerPublicacionesPorIdDistrito(this.idDistritoCom);
      this.resetForm();
      this.obtenerPublicacionesPorIdDistrito(this.idDistritoCom);
      console.log('Emergencia registrado', response);
    }, error => {
      console.error('Error al registrar Emergencia', error);
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
      this.obtenerPublicacionesPorIdDistrito(this.idDistritoCom);
      this.resetForm()
      console.log('Emergencia modificado', response);
    }, error => {
      console.error('Error al modificar Emergencia', error);
      this.validarFormulario();
    }
  );
  }
}

titulo(titulo: any, id: number) {
  this.title = `${titulo} Emergencia`
  titulo == "Crear" ? this.nameBoton = "Guardar" : this.nameBoton = "Modificar"
  if (id != null) {
    this.id = id
    this.obtenerAlumnoPorId(id)
  }
}

crearEditarAlumno(boton: any) {
  if (boton == "Guardar") {
    this.formAlumno.controls['estado'].setValue('A')
    this.formAlumno.controls['img'].setValue('A')
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
  this.formAlumno.controls['img'].setValue('A')
  this.formAlumno.controls['emergencia'].setValue(this.emer)
}

cerrarBoton() {
  this.resetForm()
  this.cerrarModal()
}

alertRegistro() {
  if (this.formAlumno.valid) {
    Swal.fire({
      title: '¿Estás seguro de registrar el Emergencia?',
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
      title: '¿Estás seguro de modificar la Emergencia?',
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
    title: "Emergencia "+titulo,
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

chatId: number | null = null;

abrirChat(id: number): void {
  console.log(`Cargando chat para la publicacion con ID: ${id}`);
  this.chatId = id; // Guardamos el ID del alumno para referencias futuras
  this.obtenerPublicacionesPorIdDistrito(this.idDistritoCom);
  this.cargarMensajes(id);

  // Abrimos el modal
  const chatModalElement = document.getElementById('modalChat');
  const chatModal = new bootstrap.Modal(chatModalElement);
  chatModal.show();
}

noChats: boolean = false;

cargarMensajes(id: number): void {
  console.log(`Cargando mensajes para el ID: ${id}`);

  this._alumnoService.obtenerMensajes(id).subscribe(
    (data: any) => {
      if (data && data.chats) {
        console.log(`Mensajes cargados para el ID ${id}:`, data.chats);
        this.listChats = data.chats;
        this.noChats = this.listChats.length === 0;
      } else {
        console.warn(`No se encontraron mensajes para el ID ${id}.`);
        this.listChats = [];
        this.noChats = true;
      }
    },
    (error) => {
      console.error(`Error al cargar mensajes para el ID ${id}:`, error);
      this.noChats = true;
    }
  );
}

enviarMensaje(): void {
  if (this.formChat.valid) {
    const mensaje = {
      mensaje: this.formChat.value.mensaje, // Formato estándar de fecha// Cambia esto al ID del usuario autenticado
    };

    this._alumnoService.enviarMensaje(this.chatId, mensaje).subscribe(
      (response) => {
        console.log('Mensaje enviado:', response);
        this.listChats.push(response); // Actualizamos la lista de chats
        this.formChat.reset();
        this.formAlumno.reset();
        this.cargarMensajes(this.chatId);
        //setInterval(() => this.cargarMensajes(this.chatId), 1000);
      },
      (error) => {
        console.error('Error al enviar el mensaje:', error);
      }
    );
  } else {
    console.log('Formulario inválido');
    //this.validarFormularioChat()
  }
}


}
