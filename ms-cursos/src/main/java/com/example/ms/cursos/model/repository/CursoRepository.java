package com.example.ms.cursos.model.repository;

import com.example.ms.cursos.model.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {

    @Query("select c from Curso c join fetch c.cursoAlumnos a where a.alumnoId = ?1")
    Curso findCursoByAlumnoId(Long id);

    @Modifying
    @Query("delete from CursoAlumno ca where ca.alumnoId = ?1")
    void eliminarCursoAlumnoPorId(Long id);
}
