package com.example.ms.respuestas.feign;

import com.example.commons.examenes.model.entity.Examen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ms-examenes")
public interface ExamenFeignClient {

    @GetMapping("/{id}")
    Examen obtenerExamenPorId(@PathVariable Long id);

    @GetMapping("/respondidos-por-preguntas")
    List<Long> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds);

}
