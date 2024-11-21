package com.api.sistemaaprendizajeback.service.Impl;

import com.api.sistemaaprendizajeback.dto.EstudianteDTO;
import com.api.sistemaaprendizajeback.dto.GradoDTO;
import com.api.sistemaaprendizajeback.model.Estudiante;
import com.api.sistemaaprendizajeback.model.Grado;
import com.api.sistemaaprendizajeback.repository.GradoRepository;
import com.api.sistemaaprendizajeback.service.GradoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradoServiceImpl implements GradoService {

    private final GradoRepository gradoRepository;

    public GradoServiceImpl(GradoRepository gradoRepository) {
        this.gradoRepository = gradoRepository;
    }

    @Override
    public List<GradoDTO> listarGrados() {
        List<Grado> grados = gradoRepository.findAll();
        return grados.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }


    private GradoDTO convertirADTO(Grado grado) {
        GradoDTO dto = new GradoDTO();
        dto.setId(grado.getId());
        dto.setNombre(grado.getNombreGrado());
        return dto;
    }
}
