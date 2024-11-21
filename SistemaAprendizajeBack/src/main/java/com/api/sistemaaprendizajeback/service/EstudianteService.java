package com.api.sistemaaprendizajeback.service;

import com.api.sistemaaprendizajeback.dto.EstudianteDTO;
import com.api.sistemaaprendizajeback.model.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EstudianteService {

    Page<EstudianteDTO> obtenerEstudiantesPaginados(Pageable pageable);
    Optional<EstudianteDTO> obtenerEstudiantePorId(Long id);
    EstudianteDTO guardarEstudiante(Estudiante estudiante);
    void eliminarEstudiantePorId(Long id);
    Page<EstudianteDTO> obtenerEstudiantesPorGradoPaginados(Long gradoId, Pageable pageable);
    Page<EstudianteDTO> obtenerEstudiantesPorFiltro(Long gradoId, String searchQuery, Pageable pageable);
    boolean existsByDni(String dni);
    boolean existsByCorreoElectronico(String correoElectronico);
    boolean existsByUsername(String username);
}
