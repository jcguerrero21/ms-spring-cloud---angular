package com.example.msusuarios.controller;

import com.example.commons.controller.CommonController;
import com.example.commons.usuarios.entity.Alumno;
import com.example.msusuarios.service.IAlumnoService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RestController
public class AlumnoController extends CommonController<Alumno, IAlumnoService> {

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable Long id) {
        Optional<Alumno> o = service.findById(id);
        if (o.isEmpty() || Objects.isNull(o.get().getFoto())) {
            return ResponseEntity.notFound().build();
        }

        Resource imagen= new ByteArrayResource(o.get().getFoto());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id) throws IOException {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Alumno> alumnoOptional = service.findById(id);
        if (alumnoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDb = alumnoOptional.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term) {
        return ResponseEntity.ok(service.findByNombreOrApellido(term));
    }

    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result,
                                          @RequestParam MultipartFile archivo) throws IOException {
        if (Objects.nonNull(archivo)) {
            alumno.setFoto(archivo.getBytes());
        }
        return super.crear(alumno, result);
    }

    @PutMapping("/editar-con-foto/{id}")
    public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result,
                                           @PathVariable Long id, @RequestParam MultipartFile archivo) throws IOException {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Alumno> alumnoOptional = service.findById(id);
        if (alumnoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDb = alumnoOptional.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());

        if (Objects.nonNull(archivo)) {
            alumnoDb.setFoto(archivo.getBytes());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
    }

}
