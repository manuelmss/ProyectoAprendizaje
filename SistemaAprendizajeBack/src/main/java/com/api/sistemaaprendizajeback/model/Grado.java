package com.api.sistemaaprendizajeback.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Grados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NombreGrado", nullable = false, length = 100)
    private String nombreGrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocenteID", nullable = false)
    private Usuario docente;

}
