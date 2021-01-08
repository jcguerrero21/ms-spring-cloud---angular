package com.example.ms.cursos.feign;

import com.example.commons.usuarios.entity.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-usuarios")
public interface AlumnoFeignClient {

    @GetMapping("/alumnos-por-curso")
    Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
