import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl = environment.url;
  private usuUrl = environment.usuarioUrl

  private login: string = `${this.baseUrl}/login`

  private registro: string =`${this.usuUrl}/registrar`

  private distritos: string = `${this.usuUrl}/dis`

  constructor(
    private http: HttpClient
  ) { }

  ingresar(request : any): Observable<any> {
    return this.http.post(`${this.login}`, request, {
      observe: 'response'
    }).pipe(map((response : HttpResponse<any>) => {
      const body = response.body;
      const headers = response.headers;

      const beaberToken = headers.get('Authorization');
      const token = beaberToken.replace('Bearer ', '');
      localStorage.setItem('token', token);
      return body;
    }))
  }
  listarDistritos():Observable<any>{
    return this.http.get(this.distritos)
  }

  registrar(usuario: any):Observable<any>{
    console.log('Datos enviados al backend',usuario);
    return this.http.post(`${this.usuUrl}/registrar`, usuario)
  }

 token(){
    return localStorage.getItem('token');
 }

 logout() {
  localStorage.removeItem('token');
}
}
