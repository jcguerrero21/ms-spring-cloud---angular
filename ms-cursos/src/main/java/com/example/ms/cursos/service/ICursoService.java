package com.example.ms.cursos.service;

import com.example.commons.service.ICommonService;
import com.example.commons.usuarios.entity.Alumno;
import com.example.ms.cursos.model.entity.Curso;

public interface ICursoService extends ICommonService<Curso> {

    Curso findCursoByAlumnoId(Long id);

    Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);

    Iterable<Alumno> obtenerAlumnosPorCurso(Iterable<Long> ids);

    void eliminarCursoAlumnoPorId(Long id);

}
