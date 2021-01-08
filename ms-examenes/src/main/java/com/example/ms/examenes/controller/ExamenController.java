package com.example.ms.examenes.controller;

import com.example.commons.controller.CommonController;
import com.example.commons.examenes.model.entity.Examen;
import com.example.ms.examenes.service.IExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, IExamenService> {

    @GetMapping("/respondidos-por-preguntas")
    public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds) {
        return ResponseEntity.ok().body(service.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Examen> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Examen examenDb = o.get();
        examenDb.setNombre(examen.getNombre());

        examenDb.getPreguntas()
                .stream()
                .filter(pdb -> !examen.getPreguntas().contains(pdb))
                .forEach(examenDb::removePregunta);

        examenDb.setPreguntas(examen.getPreguntas());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term) {
        return ResponseEntity.ok(service.findByNombre(term));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas() {
        return ResponseEntity.ok(service.findAllAsignaturas());
    }
}
