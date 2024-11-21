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
@Table(name = "Cuestionarios")
public class Cuestionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Titulo", nullable = false, length = 100)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TemaID", nullable = false)
    private Tema tema;

    @Enumerated(EnumType.STRING)
    @Column(name = "Dificultad", nullable = false)
    private Dificultad dificultad;

}
