package com.example.ms.respuestas.service.impl;

import com.example.commons.examenes.model.entity.Examen;
import com.example.commons.examenes.model.entity.Pregunta;
import com.example.ms.respuestas.feign.ExamenFeignClient;
import com.example.ms.respuestas.model.entity.Respuesta;
import com.example.ms.respuestas.model.repository.RespuestaRepository;
import com.example.ms.respuestas.service.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaServiceImpl implements IRespuestaService {

    @Autowired
    private RespuestaRepository repository;

    @Autowired
    private ExamenFeignClient examenFeignClient;

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return repository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        Examen examen = examenFeignClient.obtenerExamenPorId(examenId);
        List<Pregunta> preguntas = examen.getPreguntas();
        List<Long> preguntasIds = preguntas.stream().map(Pregunta::getId).collect(Collectors.toList());
        List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntasIds);
        respuestas = respuestas.stream().map(r -> {
            preguntas.forEach(p -> {
                if (p.getId().equals(r.getPreguntaId())) {
                    r.setPregunta(p);
                }
            });
            return r;
        }).collect(Collectors.toList());

        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestasByALumno(Long alumnoId) {
        List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findByAlumnoId(alumnoId);
        List<Long> examenIds = Collections.emptyList();

        if (respuestasAlumno.size() > 0) {
            List<Long> preguntaIds = respuestasAlumno.stream().map(Respuesta::getPreguntaId).collect(Collectors.toList());
            examenIds = examenFeignClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
        }

        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
        return repository.findByAlumnoId(alumnoId);
    }

}
