import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlumnosService {
  private baseUrl = environment.apiUrl;
  private alumnos: string = `${this.baseUrl}/alumnos`
  private usuUrl = environment.usuarioUrl
  private distritos: string = `${this.usuUrl}/dis`

  constructor(
    private http:HttpClient
  ) { }

  listarAlumnos(): Observable<any> {
    return this.http.get(this.alumnos)
  }
  usuarioActual(): Observable<any> {
    return this.http.get(`${this.usuUrl}/actual`)
  }

  eliminarAlumnos(id: number): Observable<any> {
    return this.http.delete(`${this.alumnos}/${id}`)
  }

  generarAlumnos(alumno: any): Observable<any> {
    console.log('Datos enviados al backend:', alumno);
    return this.http.post(`${this.baseUrl}/alumnos`, alumno);
  }

  obtenerAlumnos(id: number): Observable<any> {
    return this.http.get(`${this.alumnos}/${id}`)
  }
  crearChat(id:number,chat: any): Observable<any> {
    console.log('Datos enviados al backend:', chat);
    return this.http.post(`${this.baseUrl}/${id}/chat`, chat);
  }
  

  editarAlumnos(id: number, request: any): Observable<any> {
    return this.http.put(`${this.alumnos}/${id}`, request)
  }

  obtenerMensajes(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/alumnos/${id}/chat`)
  }

  enviarMensaje(alumnoId: number, mensaje: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/alumnos/${alumnoId}/chat`,mensaje)
  }
  listarpublicacionesPorIdDistrito(idDistrito: number,emergencia:string): Observable<any> {
    return this.http.get(`${this.alumnos}/${idDistrito}/${emergencia}/waa`)
  }

  listarBomberosPorDistrito(idDistrito: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/bomberos/distrito/${idDistrito}`)
  }
  listarComisariaPorDistrito(idDistrito: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/comisaria/distrito/${idDistrito}`)
  }
  listarDistritos():Observable<any>{
    return this.http.get(this.distritos)
  }
  

}
