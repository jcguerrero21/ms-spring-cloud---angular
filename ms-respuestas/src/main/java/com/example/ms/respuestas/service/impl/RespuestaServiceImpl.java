package com.example.ms.respuestas.service.impl;

import com.example.ms.respuestas.model.entity.Respuesta;
import com.example.ms.respuestas.model.repository.RespuestaRepository;
import com.example.ms.respuestas.service.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaServiceImpl implements IRespuestaService {

    @Autowired
    private RespuestaRepository repository;

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return repository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        return repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestasByALumno(Long alumnoId) {
        List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findExamenesIdsConRespuestasByAlumno(alumnoId);
        List<Long> examenIds = respuestasAlumno.stream()
                .map(r -> r.getPregunta().getExamen().getId())
                .distinct().collect(Collectors.toList());

        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
        return repository.findByAlumnoId(alumnoId);
    }

}
