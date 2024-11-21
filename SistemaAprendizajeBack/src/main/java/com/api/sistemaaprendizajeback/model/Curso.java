package com.api.sistemaaprendizajeback.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Lob
    @Column(name = "Imagen")
    private byte[] imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocenteID", nullable = false)
    private Usuario docente;
}
