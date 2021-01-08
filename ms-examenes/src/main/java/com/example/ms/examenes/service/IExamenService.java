package com.example.ms.examenes.service;

import com.example.commons.examenes.model.entity.Asignatura;
import com.example.commons.examenes.model.entity.Examen;
import com.example.commons.service.ICommonService;

import java.util.List;

public interface IExamenService extends ICommonService<Examen> {

    List<Examen> findByNombre(String term);

    Iterable<Asignatura> findAllAsignaturas();

    Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds);

}
