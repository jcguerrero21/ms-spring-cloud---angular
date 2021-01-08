package com.example.ms.examenes.service.impl;

import com.example.commons.examenes.model.entity.Asignatura;
import com.example.commons.examenes.model.entity.Examen;
import com.example.commons.service.impl.CommonServiceImpl;
import com.example.ms.examenes.model.repository.AsignaturaRepository;
import com.example.ms.examenes.model.repository.ExamenRepository;
import com.example.ms.examenes.service.IExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements IExamenService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String term) {
        return repository.findByNombre(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Asignatura> findAllAsignaturas() {
        return asignaturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds) {
        return repository.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds);
    }
}
