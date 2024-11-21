package com.api.sistemaaprendizajeback.controller;


import com.api.sistemaaprendizajeback.dto.ApiResponse;
import com.api.sistemaaprendizajeback.dto.EstudianteDTO;
import com.api.sistemaaprendizajeback.dto.GradoDTO;
import com.api.sistemaaprendizajeback.service.GradoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grados")
@CrossOrigin(origins = "*")
public class GradoController {

    private final GradoService gradoService;

    public GradoController(GradoService gradoService) {
        this.gradoService = gradoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GradoDTO>>> listarGrados() {
        List<GradoDTO> grados = gradoService.listarGrados();

        if (grados == null || grados.isEmpty()) {
            ApiResponse<List<GradoDTO>> response = new ApiResponse<>(
                    false,
                    "No se encontraron grados",
                    null,
                    HttpStatus.NO_CONTENT.value()
            );
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        ApiResponse<List<GradoDTO>> response = new ApiResponse<>(
                true,
                "Lista de Grados",
                grados,
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
