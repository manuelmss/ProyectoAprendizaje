package com.api.sistemaaprendizajeback.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Preguntas")
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Texto", nullable = false, columnDefinition = "TEXT")
    private String texto; // El texto de la pregunta

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo", nullable = false)
    private TipoPregunta tipo; // Tipo de pregunta (multiple, truefalse, ordering, etc.)

    @ElementCollection
    @CollectionTable(name = "Opciones", joinColumns = @JoinColumn(name = "PreguntaID"))
    @Column(name = "Opcion")
    private Set<String> opciones = new HashSet<>(); // Opciones para multiple-choice

    @Column(name = "RespuestaCorrecta", nullable = false)
    private String respuestaCorrecta; // La respuesta correcta

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CuestionarioID", nullable = false)
    private Cuestionario cuestionario; // Relación con el cuestionario
}
