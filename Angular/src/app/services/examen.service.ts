import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BASE_ENDPOINT } from '../config/app';
import { Examen } from '../models/examen';
import { CommonService } from './common.service';
import { Observable } from 'rxjs';
import { Asignatura } from '../models/asignatura';

@Injectable({
  providedIn: 'root'
})
export class ExamenService extends CommonService<Examen> {

  protected baseEndpoint = `${BASE_ENDPOINT}/examenes`;
  
  constructor(http: HttpClient) {
    super(http);
  }

  public findAllAsignatura(): Observable<Asignatura[]> {
    return this.http.get<Asignatura[]>(`${this.baseEndpoint}/asignaturas`);
  }

  public filtrarPorNombre(nombre: string): Observable<Examen[]> {
    return this.http.get<Examen[]>(`${this.baseEndpoint}/filtrar/${nombre}`);
  }

}
