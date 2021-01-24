import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs';
import { BASE_ENDPOINT } from '../config/app';
import { Examen } from '../models/examen';
import { Alumno } from '../models/alumno';

@Injectable({
  providedIn: 'root'
})
export class RespuestaService {

  private headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  private baseEndpoint = `${BASE_ENDPOINT}/respuestas`;

  constructor(private http: HttpClient) { }

  crear(respuestas: Respuesta[]): Observable<Respuesta[]> {
    return this.http.post<Respuesta[]>(this.baseEndpoint, respuestas, { headers: this.headers });
  }

  obtenerRespuestasPorAlumnoPorExamen(alumno: Alumno, examen: Examen): Observable<Respuesta[]> {
    return this.http.get<Respuesta[]>(`${this.baseEndpoint}/alumno/${alumno.id}/examen/${examen.id}`);
  }
}
