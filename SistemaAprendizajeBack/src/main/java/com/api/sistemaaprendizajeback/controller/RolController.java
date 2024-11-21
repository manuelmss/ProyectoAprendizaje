package com.api.sistemaaprendizajeback.controller;

import com.api.sistemaaprendizajeback.dto.ApiResponse;
import com.api.sistemaaprendizajeback.model.Rol;
import com.api.sistemaaprendizajeback.service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Rol>>> listarRoles() {
        List<Rol> roles = rolService.obtenerTodosLosRoles();
        if (roles == null || roles.isEmpty()) {
            ApiResponse<List<Rol>> response = new ApiResponse<>(
                    false,
                    "No se encontraron roles",
                    null,
                    HttpStatus.NOT_FOUND.value()
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<List<Rol>> response = new ApiResponse<>(
                true,
                "Lista de roles obtenida con éxito",
                roles,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}
