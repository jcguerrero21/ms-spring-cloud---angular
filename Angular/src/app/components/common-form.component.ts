import { Directive, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import Swal from 'sweetalert2'
import { CommonService } from '../services/common.service';
import { Generic } from '../models/generic';

@Directive()
export abstract class CommonFormComponent<E extends Generic, S extends CommonService<E>> implements OnInit {

  titulo: string;
  model: E;
  error: any;
  protected redirect: string;
  protected nombreModel: string;
  
  

  constructor(protected service: S, protected router: Router, protected route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = (params.get('id') != null) ? +params.get('id'): 0;
      if (id) {
        this.service.ver(id).subscribe(model => {
          this.model = model;
          this.titulo = 'Editar ' + this.nombreModel;
        });
      }
    })
  }
  
  crear(): void {   
    this.service.crear(this.model).subscribe(m => {
      Swal.fire('Nuevo:', `${this.nombreModel} ${m.nombre} creado con éxito`, 'success');
      this.router.navigate([this.redirect]);
    }, err => {
      if (err.status === 400) {
        this.error = err.error;
      }
    });
  }

  editar() {
    this.service.editar(this.model).subscribe(m => {
      Swal.fire('Modificado:', `${this.nombreModel} ${m.nombre} actualizado con éxito`, 'info');
      this.router.navigate([this.redirect]);
    }, err => {
      if (err.status === 400) {
        this.error = err.error;
      }
    });
  }

}
