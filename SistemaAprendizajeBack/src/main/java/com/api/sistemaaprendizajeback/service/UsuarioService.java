package com.api.sistemaaprendizajeback.service;

import com.api.sistemaaprendizajeback.dto.UsuarioDTO;
import com.api.sistemaaprendizajeback.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Page<UsuarioDTO> obtenerUsuariosPaginados(Pageable pageable);
    Optional<UsuarioDTO> obtenerUsuarioPorId(Long id);
    UsuarioDTO guardarUsuario(Usuario usuario);
    void eliminarUsuarioPorId(Long id);
    Page<UsuarioDTO> obtenerUsuariosPorRolPaginados(Long rolId, Pageable pageable);
    Page<UsuarioDTO> obtenerUsuariosPorFiltro(Long rolId, String searchQuery, Pageable pageable);
    boolean existsByCorreoElectronico(String correoElectronico);
    boolean existsByUsername(String username);
    boolean existsByDni(String dni);
    List<UsuarioDTO> obtenerUsuariosConRolDocente();
}
