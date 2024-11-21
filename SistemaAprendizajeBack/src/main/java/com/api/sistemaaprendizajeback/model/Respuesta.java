package com.api.sistemaaprendizajeback.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Respuestas")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PreguntaID", nullable = false)
    private Pregunta pregunta; // Relación con la pregunta

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EstudianteID", nullable = false)
    private Estudiante estudiante; // Relación con el estudiante que respondió

    @Column(name = "RespuestaEstudiante", nullable = false)
    private String respuestaEstudiante; // La respuesta proporcionada por el estudiante

    @Column(name = "EsCorrecta", nullable = false)
    private Boolean esCorrecta; // Indica si la respuesta es correcta o no
}
