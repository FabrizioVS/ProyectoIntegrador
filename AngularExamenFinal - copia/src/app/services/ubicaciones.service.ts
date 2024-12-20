import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UbicacionesService {
  private apiBaseUrl = environment.apiUrl; // URL base de la API

  constructor(private http: HttpClient) {}

  // Obtener Comisarías para un distrito
  obtenerComisarias(distrito: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/comisarias/distrito/${distrito}`);
  }

  // Obtener Hospitales para un distrito
  obtenerHospitales(distrito: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/hospitales/distrito/${distrito}`);
  }

  // Obtener Estaciones de Bomberos para un distrito
  obtenerEstacionesBomberos(distrito: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/bomberos/distrito/${distrito}`);
  }

  // Obtener los Distritos
  obtenerDistritos(): Observable<{ id: number; descripcion: string }[]> {
    return this.http.get<{ id: number; descripcion: string }[]>(`${this.apiBaseUrl}/distritos`);
  }

  // Obtener ubicaciones según tipo y distrito
  obtenerPorTipoYDistrito(tipo: string, distritoId: number): Observable<any[]> {
    // Imprime los parámetros que estás enviando
    console.log(`Cargando datos para tipo: ${tipo} y distrito: ${distritoId}`);

    return this.http.get<any[]>(`${this.apiBaseUrl}/${tipo}/distrito/${distritoId}`);
  }
}


