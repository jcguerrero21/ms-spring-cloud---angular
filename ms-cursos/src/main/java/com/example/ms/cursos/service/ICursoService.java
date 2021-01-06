package com.example.ms.cursos.service;

import com.example.commons.service.ICommonService;
import com.example.ms.cursos.model.entity.Curso;

public interface ICursoService extends ICommonService<Curso> {

    Curso findCursoByAlumnoId(Long id);

    Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);

}
