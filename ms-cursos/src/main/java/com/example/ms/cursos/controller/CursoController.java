package com.example.ms.cursos.controller;

import com.example.commons.controller.CommonController;
import com.example.commons.examenes.model.entity.Examen;
import com.example.commons.usuarios.entity.Alumno;
import com.example.ms.cursos.model.entity.Curso;
import com.example.ms.cursos.model.entity.CursoAlumno;
import com.example.ms.cursos.service.ICursoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, ICursoService> {

    @Value("${config.balanceador.test}")
    private String balanceadorTest;

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
        service.eliminarCursoAlumnoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Override
    public ResponseEntity<?> listar() {
        List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map(c -> {
            c.getCursoAlumnos().forEach(ca -> {
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                c.addAlumno(alumno);
            });
            return c;
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(cursos);
    }

    @GetMapping("/pagina")
    @Override
    public ResponseEntity<?> listar(Pageable pageable) {
        Page<Curso> cursos = service.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos().forEach(ca -> {
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                curso.addAlumno(alumno);
            });
            return curso;
        });
        return ResponseEntity.ok().body(cursos);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Curso> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso curso = o.get();

        if (!curso.getCursoAlumnos().isEmpty()) {
            List<Long> ids = curso.getCursoAlumnos().stream()
                    .map(CursoAlumno::getAlumnoId)
                    .collect(Collectors.toList());

            List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);

            curso.setAlumnos(alumnos);
        }

        return ResponseEntity.ok().body(curso);
    }

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("balanceador", balanceadorTest);
        response.put("cursos", service.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Curso> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = o.get();
        dbCurso.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = o.get();

        alumnos.forEach(a -> {
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(a.getId());
            cursoAlumno.setCurso(dbCurso);
            dbCurso.addCursoAlumno(cursoAlumno);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = o.get();
        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());
        dbCurso.removeCursoAlumno(cursoAlumno);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
        Curso curso = service.findCursoByAlumnoId(id);

        if (curso != null) {
            List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
            if (examenesIds != null && examenesIds.size() >0) {
                List<Examen> examenes = curso.getExamenes().stream().peek(examen -> {
                    if (examenesIds.contains(examen.getId())) {
                        examen.setRespondido(true);
                    }
                }).collect(Collectors.toList());

                curso.setExamenes(examenes);
            }

        }
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = o.get();

        examenes.forEach(dbCurso::addExamen);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = o.get();

        dbCurso.removeExamen(examen);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

}
