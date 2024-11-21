package com.api.sistemaaprendizajeback.service.Impl;

import com.api.sistemaaprendizajeback.dto.UsuarioDTO;
import com.api.sistemaaprendizajeback.model.Usuario;
import com.api.sistemaaprendizajeback.repository.UsuarioRepository;
import com.api.sistemaaprendizajeback.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Page<UsuarioDTO> obtenerUsuariosPaginados(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(this::convertirADTO);
    }

    @Override
    public Optional<UsuarioDTO> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADTO);
    }

    @Override
    public UsuarioDTO guardarUsuario(Usuario usuario) {
        usuario.setFechaInscripcion(LocalDateTime.now());
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    @Override
    public void eliminarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Page<UsuarioDTO> obtenerUsuariosPorRolPaginados(Long rolId, Pageable pageable) {
        return usuarioRepository.findByRolId(rolId, pageable)
                .map(this::convertirADTO);
    }

    @Override
    public Page<UsuarioDTO> obtenerUsuariosPorFiltro(Long rolId, String searchQuery, Pageable pageable) {
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        Page<Usuario> usuarios = usuarioRepository.findByRolIdAndSearch(rolId, searchQuery, pageableWithSort);
        return usuarios.map(this::convertirADTO);
    }

    @Override
    public boolean existsByCorreoElectronico(String correoElectronico) {
        return usuarioRepository.existsByCorreoElectronico(correoElectronico);
    }

    @Override
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }

    @Override
    public List<UsuarioDTO> obtenerUsuariosConRolDocente() {
        List<Usuario> usuariosDocentes = usuarioRepository.findByRolNombreRol("Docente");
        return usuariosDocentes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setCorreoElectronico(usuario.getCorreoElectronico());
        dto.setContrasena(usuario.getContrasena());
        dto.setNumeroTelefono(usuario.getNumeroTelefono());
        dto.setDireccion(usuario.getDireccion());
        dto.setFotoPerfil(usuario.getFotoPerfil());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setGenero(usuario.getGenero().toString());
        dto.setEstado(usuario.getEstado());
        dto.setFechaInscripcion(usuario.getFechaInscripcion());
        dto.setRolNombre(usuario.getRol() != null ? usuario.getRol().getNombreRol() : null);
        dto.setDni(usuario.getDni());
        dto.setUsername(usuario.getUsername());
        return dto;
    }

}
