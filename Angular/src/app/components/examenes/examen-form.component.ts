import { Component } from '@angular/core';
import { Examen } from 'src/app/models/examen';
import { CommonFormComponent } from '../common-form.component';
import { ExamenService } from '../../services/examen.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Asignatura } from '../../models/asignatura';
import { Pregunta } from 'src/app/models/pregunta';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-examen-form',
  templateUrl: './examen-form.component.html',
  styleUrls: ['./examen-form.component.scss']
})
export class ExamenFormComponent extends CommonFormComponent<Examen, ExamenService> {

  asignaturasPadre: Asignatura[] = [];
  asignaturasHija: Asignatura[] = [];
  errorPreguntas: String;

  constructor(service: ExamenService, router: Router, route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Examen';
    this.model = new Examen();
    this.redirect = '/examenes';
    this.nombreModel = Examen.name;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = (params.get('id') != null) ? +params.get('id') : 0;
      if (id) {
        this.service.ver(id).subscribe(model => {
          this.model = model;
          this.titulo = 'Editar ' + this.nombreModel;
          this.cargarHijos();
        });
      }
    });

    this.service.findAllAsignatura().subscribe(asignaturas => this.asignaturasPadre = asignaturas.filter(a => !a.padre));
  }

  public crear(): void {
    if (this.model.preguntas.length === 0) {
      this.errorPreguntas = 'Examen debe tener preguntas';
      // Swal.fire('Error Preguntas', 'Examen debe tener preguntar', 'error');
      return;
    }
    this.errorPreguntas = undefined;
    this.eliminarPreguntasVacias();
    super.crear();
  }

  public editar(): void {
    if (this.model.preguntas.length === 0) {
      this.errorPreguntas = 'Examen debe tener preguntas';
      // Swal.fire('Error Preguntas', 'Examen debe tener preguntar', 'error');
      return;
    }
    this.errorPreguntas = undefined;
    this.eliminarPreguntasVacias();
    super.editar();
  }

  cargarHijos(): void {
    this.asignaturasHija = this.model.asignaturaPadre ? this.model.asignaturaPadre.hijos : [];
  }

  compararAsignatura(a1: Asignatura, a2: Asignatura): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined) ? false : a1.id === a2.id;
  }

  agregarPregunta(): void {
    this.model.preguntas.push(new Pregunta());
  }

  asignarTexto(pregunta: Pregunta, event: any): void {
    pregunta.texto = event.target.value;
  }

  eliminarPregunta(pregunta): void {
    this.model.preguntas = this.model.preguntas.filter(p => pregunta.texto !== p.texto);
  }

  eliminarPreguntasVacias(): void {
    this.model.preguntas = this.model.preguntas.filter(p => p.texto != null && p.texto.length > 0);
  }

}
