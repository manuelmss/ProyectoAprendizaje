package com.api.sistemaaprendizajeback.controller;

import com.api.sistemaaprendizajeback.dto.ApiResponse;
import com.api.sistemaaprendizajeback.dto.EstudianteDTO;
import com.api.sistemaaprendizajeback.dto.UsuarioDTO;
import com.api.sistemaaprendizajeback.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestParam String username, @RequestParam String password) {
        Object authenticatedUser = authService.authenticate(username, password);

        if (authenticatedUser != null) {
            if (authenticatedUser instanceof EstudianteDTO) {
                ApiResponse<EstudianteDTO> response = new ApiResponse<>(
                        true,
                        "Login exitoso",
                        (EstudianteDTO) authenticatedUser,
                        HttpStatus.OK.value()
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (authenticatedUser instanceof UsuarioDTO) {
                ApiResponse<UsuarioDTO> response = new ApiResponse<>(
                        true,
                        "Login exitoso",
                        (UsuarioDTO) authenticatedUser,
                        HttpStatus.OK.value()
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        ApiResponse<String> errorResponse = new ApiResponse<>(
                false,
                "Invalido username o contraseña",
                null,
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
