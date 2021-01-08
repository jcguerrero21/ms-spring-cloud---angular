package com.example.ms.respuestas.service;

import com.example.ms.respuestas.model.entity.Respuesta;

public interface IRespuestaService {

    Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

    Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

    Iterable<Long> findExamenesIdsConRespuestasByALumno(Long alumnoId);

    Iterable<Respuesta> findByAlumnoId(Long alumnoId);
}
