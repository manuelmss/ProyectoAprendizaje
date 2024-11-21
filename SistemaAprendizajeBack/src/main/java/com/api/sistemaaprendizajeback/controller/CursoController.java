package com.api.sistemaaprendizajeback.controller;

import com.api.sistemaaprendizajeback.dto.ApiResponse;
import com.api.sistemaaprendizajeback.dto.UsuarioDTO;
import com.api.sistemaaprendizajeback.model.Curso;
import com.api.sistemaaprendizajeback.model.Usuario;
import com.api.sistemaaprendizajeback.service.CursoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // Listar todos los cursos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Curso>>> listarCursos() {
        List<Curso> cursos = cursoService.getAllCursos();

        if (cursos == null || cursos.isEmpty()) {
            ApiResponse<List<Curso>> response = new ApiResponse<>(
                    false,
                    "No se encontraron cursos",
                    null,
                    HttpStatus.NO_CONTENT.value()
            );
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        ApiResponse<List<Curso>> response = new ApiResponse<>(
                true,
                "Lista de Cursos",
                cursos,
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener un curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Curso>> obtenerCurso(@PathVariable Long id) {
        Curso curso = cursoService.getCursoById(id);

        if (curso == null) {
            ApiResponse<Curso> response = new ApiResponse<>(
                    false,
                    "Curso no encontrado",
                    null,
                    HttpStatus.NOT_FOUND.value()
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<Curso> response = new ApiResponse<>(
                true,
                "Curso encontrado",
                curso,
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Crear un curso
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Boolean>> crearCurso(
            @Valid @RequestPart("curso") Curso curso,
            @RequestPart(name = "imagen", required = false) MultipartFile imagen
    ) throws IOException {

        try {
            if (imagen != null && !imagen.isEmpty()) {
                byte[] fotoArchivo = imagen.getBytes();
                curso.setImagen(fotoArchivo);
            }

            cursoService.createCurso(curso);
            ApiResponse<Boolean> response = new ApiResponse<>(
                    true,
                    "Curso creado con éxito",
                    true,
                    HttpStatus.CREATED.value()
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<Boolean> response = new ApiResponse<>(
                    false,
                    "Error al crear el curso",
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Boolean>> actualizarCurso(
            @PathVariable Long id,
            @Valid @RequestPart("curso") Curso curso,
            @RequestPart(name = "imagen", required = false) MultipartFile imagen
    ) throws IOException {


        ApiResponse<Boolean> response;

        try {
            Curso cursoExistente = cursoService.getCursoById(id);

            if (cursoExistente == null) {
                throw new EntityNotFoundException("Curso no encontrado");
            }

            if (imagen != null && !imagen.isEmpty()) {
                byte[] fotoArchivo = imagen.getBytes();
                curso.setImagen(fotoArchivo);
            } else {
                curso.setImagen(cursoExistente.getImagen());
            }

            curso.setId(id);
            cursoService.createCurso(curso);

            response = new ApiResponse<>(
                    true,
                    "Curso actualizado con éxito",
                    true,
                    HttpStatus.OK.value()
            );

        } catch (Exception e) {

            response = new ApiResponse<>(
                    false,
                    "Error al actualizar el curso: " + e.getMessage(),
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }

        return ResponseEntity.ok(response);
    }


    // Eliminar un curso
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarCurso(@PathVariable Long id) {
        try {
            cursoService.deleteCurso(id);
            ApiResponse<Void> response = new ApiResponse<>(
                    true,
                    "Curso eliminado exitosamente",
                    null,
                    HttpStatus.OK.value()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    false,
                    "Error al eliminar el curso",
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
