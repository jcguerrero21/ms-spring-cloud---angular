package com.example.ms.cursos.service.impl;

import com.example.commons.service.impl.CommonServiceImpl;
import com.example.commons.usuarios.entity.Alumno;
import com.example.ms.cursos.feign.AlumnoFeignClient;
import com.example.ms.cursos.feign.RespuestaFeignClient;
import com.example.ms.cursos.model.entity.Curso;
import com.example.ms.cursos.model.repository.CursoRepository;
import com.example.ms.cursos.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements ICursoService {

    @Autowired
    private RespuestaFeignClient respuestaFeignClient;

    @Autowired
    private AlumnoFeignClient alumnoFeignClient;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
        return respuestaFeignClient.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
    }

    @Override
    public Iterable<Alumno> obtenerAlumnosPorCurso(Iterable<Long> ids) {
        return alumnoFeignClient.obtenerAlumnosPorCurso(ids);
    }

    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        repository.eliminarCursoAlumnoPorId(id);
    }
}
