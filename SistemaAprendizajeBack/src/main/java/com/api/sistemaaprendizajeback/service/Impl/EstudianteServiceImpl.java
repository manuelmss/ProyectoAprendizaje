package com.api.sistemaaprendizajeback.service.Impl;

import com.api.sistemaaprendizajeback.dto.EstudianteDTO;
import com.api.sistemaaprendizajeback.model.Estudiante;
import com.api.sistemaaprendizajeback.repository.EstudianteRepository;
import com.api.sistemaaprendizajeback.service.EstudianteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Page<EstudianteDTO> obtenerEstudiantesPorGradoPaginados(Long gradoId, Pageable pageable) {
        return estudianteRepository.findByGradoId(gradoId, pageable)
                .map(this::convertirADTO);
    }

    @Override
    public Page<EstudianteDTO> obtenerEstudiantesPorFiltro(Long gradoId, String searchQuery, Pageable pageable) {
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        Page<Estudiante> estudiantes = estudianteRepository.findByGradoIdAndSearch(gradoId, searchQuery, pageableWithSort);
        return estudiantes.map(this::convertirADTO);
    }

    @Override
    public Page<EstudianteDTO> obtenerEstudiantesPaginados(Pageable pageable) {
        Page<Estudiante> estudiantes = estudianteRepository.findAll(pageable);
        return estudiantes.map(this::convertirADTO);
    }


    @Override
    public Optional<EstudianteDTO> obtenerEstudiantePorId(Long id) {
        return estudianteRepository.findById(id)
                .map(this::convertirADTO);
    }

    @Override
    public EstudianteDTO guardarEstudiante(Estudiante estudiante) {
        estudiante.setFechaInscripcion(LocalDate.now());
        Estudiante estudianteGuardado = estudianteRepository.save(estudiante);
        return convertirADTO(estudianteGuardado);
    }

    @Override
    public void eliminarEstudiantePorId(Long id) {
        estudianteRepository.deleteById(id);
    }

    @Override
    public boolean existsByDni(String dni) {
        return estudianteRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByCorreoElectronico(String correoElectronico) {
        return estudianteRepository.existsByCorreoElectronico(correoElectronico);
    }

    @Override
    public boolean existsByUsername(String username) {
        return estudianteRepository.existsByUsername(username);
    }

    private EstudianteDTO convertirADTO(Estudiante estudiante) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(estudiante.getId());
        dto.setNombre(estudiante.getNombre());
        dto.setApellidos(estudiante.getApellidos());
        dto.setCorreoElectronico(estudiante.getCorreoElectronico());
        dto.setContrasena(estudiante.getContrasena());
        dto.setNumeroTelefono(estudiante.getNumeroTelefono());
        dto.setDireccion(estudiante.getDireccion());
        dto.setFotoPerfil(estudiante.getFotoPerfil());
        dto.setFechaNacimiento(estudiante.getFechaNacimiento());
        dto.setGenero(estudiante.getGenero().toString());
        dto.setEstado(estudiante.getEstado());
        dto.setFechaInscripcion(estudiante.getFechaInscripcion());
        dto.setGradoNombre(estudiante.getGrado() != null ? estudiante.getGrado().getNombreGrado() : null);
        dto.setDni(estudiante.getDni());
        dto.setUsername(estudiante.getUsername());
        return dto;
    }
}
