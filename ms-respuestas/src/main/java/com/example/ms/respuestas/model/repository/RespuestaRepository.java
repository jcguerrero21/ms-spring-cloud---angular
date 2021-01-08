package com.example.ms.respuestas.model.repository;

import com.example.ms.respuestas.model.entity.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RespuestaRepository extends MongoRepository<Respuesta, String> {

    @Query("{'alumnoId': ?0, 'preguntaId': {$in: ?1}}")
    Iterable<Respuesta> findRespuestaByAlumnoByPreguntaIds(Long alumnoId, Iterable<Long> preguntaIds);

    @Query("{'alumnoId': ?0}")
    Iterable<Respuesta> findByAlumnoId(Long alumnoId);

}
