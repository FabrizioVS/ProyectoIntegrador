import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';

declare var bootstrap: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  usuario: any[] = []
  listarDistritos: any[] = []
  formL: FormGroup
  formUsuario: FormGroup
  nameBoton: string = "Crear";


  constructor(
    private _loginService : LoginService,
    private route: Router
  ) { }

  ngOnInit(): void {
    this.initForm()
    this.initFormRegistro();
    this.obtenerDistritos();
  }

  initForm() {
    this.formL = new FormGroup({
      email: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required])
    })
  }
  initFormRegistro() {
    this.formUsuario = new FormGroup({
      nombre:new FormControl(null,[Validators.required]),
      apellido:new FormControl(null,[Validators.required]),
      idDistrito:new FormControl(null,[Validators.required]),
      email: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required])
    })
  }

  obtenerDistritos(){
    this._loginService.listarDistritos()
    .subscribe((data)=>{
      this.listarDistritos = data.distritos; // -> este dato "distritos" es el json que mandas en el listarDistritos
      if(this.listarDistritos.length == 0){
        console.log("No hay datos")
      }
    });
  }

  login(){
    if(this.formL.valid){
      console.log("Acceso", this.formL.value)
      this._loginService.ingresar(this.formL.value)
      .subscribe({
        next: (res) => {
          console.log("Response: ", res)
          this.route.navigate(['/alumno'])
        },
        error: (err: HttpErrorResponse) => {
          this.alertaError()
        }
      });
    }
  }

  crearUsuario(formulario:any):void{
    if(this.formUsuario.valid){
      console.log('Datos enviados al servicio: ',formulario);

      this._loginService.registrar(formulario)
      .subscribe(response =>{
        this.cerrarModal();
        this.resetForm();
        console.log('Registrado existosamente: ', response);
      },error =>{
        console.log('Error al registrar',error);
      });
    }else{
      console.log('Formulario no valido: ',this.formUsuario);
    }

  }
  cerrarModal(){
    const madalElement = document.getElementById('modalUsuario')
    const modal = bootstrap.Modal.getInstance(madalElement);
    modal.hide();
  }
  resetForm(){
    this.formUsuario.reset();
  }

  cerrarBoton() {
    this.resetForm()
    this.cerrarModal()
  }

  alertRegistro() {
    if (this.formUsuario.valid) {
      Swal.fire({
        title: '¿Estás seguro de registrar al usuario?',
        icon: 'success',
        showCancelButton: true,
        confirmButtonText: 'Sí, confirmar',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.crearUsuario(this.formUsuario.value)
          this.alertaExitosa("registrado")
        }
      });
    }else{
      this.validarFormulario();
    }
  
  }
  validarFormulario() {
    Object.keys(this.formUsuario.controls).forEach(key => {
      this.formUsuario.markAllAsTouched();
      const control = this.formUsuario.get(key);
  
      if (control && control.invalid) {
        console.log(`Errores en el campo ${key}: `, control.errors);
      }
    });
  }

  alertaExitosa(titulo: any){
    Swal.fire({
      position: "top-end",
      icon: "success",
      title: "Usuario Creado",
      showConfirmButton: false,
      timer: 1500
    });
  }

  alertaError(){
    Swal.fire({
      position: "top-end",
      icon: "error",
      title: "Correo o contraseña incorrecta ",
      showConfirmButton: false,
      timer: 1500
    });
    this.formL.reset();
  }
  mostrarModalRegistro() {
    event.preventDefault();
    const modalElement = document.getElementById('modalUsuario');
    const modal = new bootstrap.Modal(modalElement);
    modal.show();
  }


}
