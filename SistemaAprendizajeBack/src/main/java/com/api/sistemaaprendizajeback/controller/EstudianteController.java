package com.api.sistemaaprendizajeback.controller;

import com.api.sistemaaprendizajeback.dto.ApiResponse;
import com.api.sistemaaprendizajeback.dto.EstudianteDTO;
import com.api.sistemaaprendizajeback.model.Estudiante;
import com.api.sistemaaprendizajeback.service.EstudianteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private static final Logger logger = LoggerFactory.getLogger(EstudianteController.class);

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<EstudianteDTO>>> listarEstudiantes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long gradoId,
            @RequestParam(required = false) String searchQuery) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantesPorFiltro(gradoId, searchQuery, pageable);

        ApiResponse<Page<EstudianteDTO>> response = new ApiResponse<>(
                true,
                "Lista de estudiantes obtenida con éxito",
                estudiantes,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> obtenerEstudiantePorId(@PathVariable Long id) {
        logger.info("Obteniendo estudiante por ID: {}", id);
        Optional<EstudianteDTO> estudiante = estudianteService.obtenerEstudiantePorId(id);
        if (estudiante.isPresent()) {
            ApiResponse<EstudianteDTO> response = new ApiResponse<>(
                    true,
                    "Estudiante obtenido con éxito",
                    estudiante.get(),
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Estudiante no encontrado, ID: {}", id);
            ApiResponse<EstudianteDTO> response = new ApiResponse<>(
                    false,
                    "Estudiante no encontrado",
                    null,
                    HttpStatus.NOT_FOUND.value()
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Boolean>> crearEstudiante(
            @Valid @RequestPart("estudiante") Estudiante estudiante,
            @RequestPart(name = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws IOException {

        logger.info("Creando nuevo estudiante: {}", estudiante.getCorreoElectronico());

        try {
            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                byte[] fotoArchivo = fotoPerfil.getBytes();
                estudiante.setFotoPerfil(fotoArchivo);
            }

            estudianteService.guardarEstudiante(estudiante);
            ApiResponse<Boolean> response = new ApiResponse<>(
                    true,
                    "Estudiante creado con éxito",
                    true,
                    HttpStatus.CREATED.value()
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Error al crear el estudiante", e);
            ApiResponse<Boolean> response = new ApiResponse<>(
                    false,
                    "Error al crear el estudiante",
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Boolean>> actualizarEstudiante(
            @PathVariable Long id,
            @Valid @RequestPart("estudiante") Estudiante estudiante,
            @RequestPart(name = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws IOException {

        logger.info("Actualizando estudiante ID: {}", id);
        ApiResponse<Boolean> response;

        try {
            EstudianteDTO estudianteExistente = estudianteService.obtenerEstudiantePorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                byte[] fotoArchivo = fotoPerfil.getBytes();
                estudiante.setFotoPerfil(fotoArchivo);
            } else {
                estudiante.setFotoPerfil(estudianteExistente.getFotoPerfil());
            }

            estudiante.setId(id);
            estudianteService.guardarEstudiante(estudiante);

            response = new ApiResponse<>(
                    true,
                    "Estudiante actualizado con éxito",
                    true,
                    HttpStatus.OK.value()
            );

        } catch (Exception e) {
            logger.error("Error al actualizar el estudiante ID: {}", id, e);
            response = new ApiResponse<>(
                    false,
                    "Error al actualizar el estudiante: " + e.getMessage(),
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarEstudiante(@PathVariable Long id) {
        logger.info("Eliminando estudiante ID: {}", id);
        estudianteService.eliminarEstudiantePorId(id);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Estudiante eliminado con éxito",
                null,
                HttpStatus.NO_CONTENT.value()
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validar-dni")
    public ResponseEntity<ApiResponse<Boolean>> validarDni(@RequestParam String dni) {
        logger.info("Validando DNI: {}", dni);
        boolean exists = estudianteService.existsByDni(dni);
        ApiResponse<Boolean> response = new ApiResponse<>(
                true,
                exists ? "El DNI ya está en uso" : "El DNI está disponible",
                exists,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validar-correo")
    public ResponseEntity<ApiResponse<Boolean>> validarCorreo(@RequestParam String correoElectronico) {
        logger.info("Validando Correo Electrónico: {}", correoElectronico);
        boolean exists = estudianteService.existsByCorreoElectronico(correoElectronico);
        ApiResponse<Boolean> response = new ApiResponse<>(
                true,
                exists ? "El correo electrónico ya está en uso" : "El correo electrónico está disponible",
                exists,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validar-username")
    public ResponseEntity<ApiResponse<Boolean>> validarUsername(@RequestParam String username) {
        logger.info("Validando Username: {}", username);
        boolean exists = estudianteService.existsByUsername(username);
        ApiResponse<Boolean> response = new ApiResponse<>(
                true,
                exists ? "El username ya está en uso" : "El username está disponible",
                exists,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}
