package com.example.msusuarios.service.impl;

import com.example.commons.service.impl.CommonServiceImpl;
import com.example.commons.usuarios.entity.Alumno;
import com.example.msusuarios.model.repository.AlumnoRepository;
import com.example.msusuarios.service.IAlumnoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements IAlumnoService {

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return repository.findByNombreOrApellido(term);
    }
}
