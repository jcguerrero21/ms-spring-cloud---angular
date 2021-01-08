package com.example.msusuarios.service;

import com.example.commons.service.ICommonService;
import com.example.commons.usuarios.entity.Alumno;

import java.util.List;

public interface IAlumnoService extends ICommonService<Alumno> {

    List<Alumno> findByNombreOrApellido(String term);

    Iterable<Alumno> findAllById(Iterable<Long> ids);

    void eliminarCursoAlumnoPorId(Long id);

}
