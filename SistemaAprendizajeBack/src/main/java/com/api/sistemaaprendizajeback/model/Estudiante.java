package com.api.sistemaaprendizajeback.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "CorreoElectronico", nullable = false, unique = true, length = 150)
    private String correoElectronico;

    @Column(name = "Contrasena", nullable = false, length = 255)
    private String contrasena;

    @Column(name = "NumeroTelefono", length = 15)
    private String numeroTelefono;

    @Column(name = "Direccion", length = 255)
    private String direccion;

    @Lob
    @Column(name = "FotoPerfil")
    private byte[] fotoPerfil;

    @Column(name = "FechaNacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "Genero")
    private Genero genero;

    @Column(name = "Estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "FechaInscripcion")
    private LocalDate fechaInscripcion;

    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaActualizacion")
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GradoID")
    private Grado grado;

    // Nuevo campo dni
    @Column(name = "Dni", nullable = false, length = 8, unique = true)
    private String dni;

    // Nuevo campo username
    @Column(name = "Username", nullable = false, length = 50, unique = true)
    private String username;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
