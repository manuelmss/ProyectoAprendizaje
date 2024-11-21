package com.api.sistemaaprendizajeback.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ranking")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la entidad Estudiante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EstudianteID", nullable = false)
    private Estudiante estudiante;

    // Relación con la entidad Cuestionario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temaID", nullable = false)
    private Tema tema;

    // Puntaje obtenido en el cuestionario
    @Column(name = "Puntaje", nullable = false)
    private Integer puntaje;

    // Trofeo obtenido (puede ser 'Oro', 'Plata', 'Bronce', 'Diamante')
    @Column(name = "Trofeo", nullable = false, length = 50)
    private String trofeo;

    // Enum con la dificultad del cuestionario
    @Enumerated(EnumType.STRING)
    @Column(name = "Dificultad", nullable = false)
    private Dificultad dificultad;

    // Fecha de registro en el ranking
    @Column(name = "FechaRegistro", nullable = false)
    private LocalDateTime fechaRegistro;

    // Método para asignar la fecha de registro automáticamente
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}
