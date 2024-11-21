package com.api.sistemaaprendizajeback.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

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
    private LocalDateTime fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "Genero")
    private Genero genero;

    @Column(name = "Estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "FechaInscripcion")
    private LocalDateTime fechaInscripcion;

    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaActualizacion")
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RolID", nullable = false)
    private Rol rol;

    @Column(name = "Dni", nullable = false, unique = true, length = 8)
    private String dni;

    @Column(name = "Username", nullable = false, unique = true, length = 50)
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
