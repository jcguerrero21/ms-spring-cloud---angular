package com.example.ms.respuestas.service.impl;

import com.example.ms.respuestas.model.entity.Respuesta;
import com.example.ms.respuestas.model.repository.RespuestaRepository;
import com.example.ms.respuestas.service.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RespuestaServiceImpl implements IRespuestaService {

    @Autowired
    private RespuestaRepository repository;

    @Override
    @Transactional(readOnly = false)
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return repository.saveAll(respuestas);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        return repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestasByALumno(Long alumnoId) {
        return repository.findExamenesIdsConRespuestasByALumno(alumnoId);
    }

}
