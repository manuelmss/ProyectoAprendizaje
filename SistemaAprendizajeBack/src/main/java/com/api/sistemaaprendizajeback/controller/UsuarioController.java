package com.api.sistemaaprendizajeback.controller;

import com.api.sistemaaprendizajeback.dto.ApiResponse;
import com.api.sistemaaprendizajeback.dto.UsuarioDTO;
import com.api.sistemaaprendizajeback.model.Usuario;
import com.api.sistemaaprendizajeback.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UsuarioDTO>>> listarUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long rolId,
            @RequestParam(required = false) String searchQuery) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioDTO> usuarios = usuarioService.obtenerUsuariosPorFiltro(rolId, searchQuery, pageable);

        ApiResponse<Page<UsuarioDTO>> response = new ApiResponse<>(
                true,
                "Lista de usuarios obtenida con éxito",
                usuarios,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> obtenerUsuarioPorId(@PathVariable Long id) {
        logger.info("Obteniendo usuario por ID: {}", id);
        Optional<UsuarioDTO> usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario.isPresent()) {
            ApiResponse<UsuarioDTO> response = new ApiResponse<>(
                    true,
                    "Usuario obtenido con éxito",
                    usuario.get(),
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Usuario no encontrado, ID: {}", id);
            ApiResponse<UsuarioDTO> response = new ApiResponse<>(
                    false,
                    "Usuario no encontrado",
                    null,
                    HttpStatus.NOT_FOUND.value()
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Boolean>> crearUsuario(
            @Valid @RequestPart("usuario") Usuario usuario,
            @RequestPart(name = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws IOException {

        logger.info("Creando nuevo usuario: {}", usuario.getCorreoElectronico());

        try {
            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                byte[] fotoArchivo = fotoPerfil.getBytes();
                usuario.setFotoPerfil(fotoArchivo);
            }

            usuarioService.guardarUsuario(usuario);
            ApiResponse<Boolean> response = new ApiResponse<>(
                    true,
                    "Usuario creado con éxito",
                    true,
                    HttpStatus.CREATED.value()
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Error al crear el usuario", e);
            ApiResponse<Boolean> response = new ApiResponse<>(
                    false,
                    "Error al crear el usuario",
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Boolean>> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestPart("usuario") Usuario usuario,
            @RequestPart(name = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws IOException {

        logger.info("Actualizando usuario ID: {}", id);
        ApiResponse<Boolean> response;

        try {
            UsuarioDTO usuarioExistente = usuarioService.obtenerUsuarioPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                byte[] fotoArchivo = fotoPerfil.getBytes();
                usuario.setFotoPerfil(fotoArchivo);
            } else {
                usuario.setFotoPerfil(usuarioExistente.getFotoPerfil());
            }

            usuario.setId(id);
            usuarioService.guardarUsuario(usuario);

            response = new ApiResponse<>(
                    true,
                    "Usuario actualizado con éxito",
                    true,
                    HttpStatus.OK.value()
            );

        } catch (Exception e) {
            logger.error("Error al actualizar el usuario ID: {}", id, e);
            response = new ApiResponse<>(
                    false,
                    "Error al actualizar el usuario: " + e.getMessage(),
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarUsuario(@PathVariable Long id) {
        logger.info("Eliminando usuario ID: {}", id);
        usuarioService.eliminarUsuarioPorId(id);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Usuario eliminado con éxito",
                null,
                HttpStatus.NO_CONTENT.value()
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validar-dni")
    public ResponseEntity<ApiResponse<Boolean>> validarDni(@RequestParam String dni) {
        logger.info("Validando DNI: {}", dni);
        boolean exists = usuarioService.existsByDni(dni);
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
        boolean exists = usuarioService.existsByCorreoElectronico(correoElectronico);
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
        boolean exists = usuarioService.existsByUsername(username);
        ApiResponse<Boolean> response = new ApiResponse<>(
                true,
                exists ? "El username ya está en uso" : "El username está disponible",
                exists,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/docentes")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerUsuariosConRolDocente() {
        List<UsuarioDTO> usuariosDocentes = usuarioService.obtenerUsuariosConRolDocente();

        ApiResponse<List<UsuarioDTO>> response;
        if (usuariosDocentes.isEmpty()) {
            response = new ApiResponse<>(false, "No se encontraron docentes", null, HttpStatus.NO_CONTENT.value());
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ApiResponse<>(true, "Lista de docentes", usuariosDocentes, HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
