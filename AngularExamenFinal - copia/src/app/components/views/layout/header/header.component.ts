import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  selectedNavItem: string = '';
  

  constructor(
    private _loginService: LoginService,
    private route: Router,
  ) {
        
   }

  ngOnInit(): void {
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

}
