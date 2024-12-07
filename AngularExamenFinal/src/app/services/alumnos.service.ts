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

  constructor(
    private http:HttpClient
  ) { }

  listarAlumnos(): Observable<any> {
    return this.http.get(this.alumnos)
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

  editarAlumnos(id: number, request: any): Observable<any> {
    return this.http.put(`${this.alumnos}/${id}`, request)
  }

}
