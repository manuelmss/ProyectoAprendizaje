package com.api.sistemaaprendizajeback.service.impl;

import com.api.sistemaaprendizajeback.model.Curso;
import com.api.sistemaaprendizajeback.repository.CursoRepository;
import com.api.sistemaaprendizajeback.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso getCursoById(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        return curso.orElseThrow(() -> new RuntimeException("Curso no encontrado con el ID: " + id));
    }

    @Override
    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public Curso updateCurso(Long id, Curso cursoDetails) {
        Curso existingCurso = getCursoById(id);

        existingCurso.setNombre(cursoDetails.getNombre());
        existingCurso.setDescripcion(cursoDetails.getDescripcion());

        existingCurso.setDocente(cursoDetails.getDocente());
        return cursoRepository.save(existingCurso);
    }

    @Override
    public void deleteCurso(Long id) {
        Curso curso = getCursoById(id);
        cursoRepository.delete(curso);
    }

    private String generateUniqueImageName(String originalImageName) {
        String extension = originalImageName.substring(originalImageName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalImageName;
    }
}
