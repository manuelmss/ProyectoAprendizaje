package com.api.sistemaaprendizajeback.service.Impl;

import com.api.sistemaaprendizajeback.model.Rol;
import com.api.sistemaaprendizajeback.repository.RolRepository;
import com.api.sistemaaprendizajeback.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }
}
