package com.example.commons.examenes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "examenes")
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 30)
    private String nombre;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createAt;

    @JsonIgnoreProperties(value = {"examen"}, allowSetters = true)
    @OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas;

    @JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Asignatura asignaturaPadre;

    @JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Asignatura asignaturaHija;

    @Transient
    private boolean respondido;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public Examen() {
        this.preguntas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas.clear();
        preguntas.forEach(this::addPregunta);
    }

    public void addPregunta(Pregunta pregunta) {
        this.preguntas.add(pregunta);
        pregunta.setExamen(this);
    }

    public void removePregunta(Pregunta pregunta) {
        this.preguntas.remove(pregunta);
        pregunta.setExamen(null);
    }

    public Asignatura getAsignaturaPadre() {
        return asignaturaPadre;
    }

    public void setAsignaturaPadre(Asignatura asignaturaPadre) {
        this.asignaturaPadre = asignaturaPadre;
    }

    public Asignatura getAsignaturaHija() {
        return asignaturaHija;
    }

    public void setAsignaturaHija(Asignatura asignaturaHija) {
        this.asignaturaHija = asignaturaHija;
    }

    public boolean isRespondido() {
        return respondido;
    }

    public void setRespondido(boolean respondido) {
        this.respondido = respondido;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Examen)) {
            return false;
        }

        Examen e = (Examen) obj;
        return this.id != null && this.id.equals(e.getId());
    }

}
