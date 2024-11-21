package com.api.sistemaaprendizajeback.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class EstudianteDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder los 100 caracteres")
    private String apellidos;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    @Size(max = 150, message = "El correo electrónico no puede exceder los 150 caracteres")
    private String correoElectronico;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    @Size(max = 9, message = "El número de teléfono no puede exceder los 9 caracteres")
    private String numeroTelefono;

    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
    private String direccion;

    private byte[] fotoPerfil;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate fechaInscripcion;

    @NotBlank(message = "El nombre del grado es obligatorio")
    private String gradoNombre;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String dni;

    // Nuevo campo username
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 50, message = "El nombre de usuario no puede exceder los 50 caracteres")
    private String username;
}
