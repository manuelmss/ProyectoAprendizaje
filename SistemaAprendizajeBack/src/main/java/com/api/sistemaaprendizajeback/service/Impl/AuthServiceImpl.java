package com.api.sistemaaprendizajeback.service.Impl;

import com.api.sistemaaprendizajeback.dto.EstudianteDTO;
import com.api.sistemaaprendizajeback.dto.UsuarioDTO;
import com.api.sistemaaprendizajeback.model.Estudiante;
import com.api.sistemaaprendizajeback.model.Usuario;
import com.api.sistemaaprendizajeback.repository.EstudianteRepository;
import com.api.sistemaaprendizajeback.repository.UsuarioRepository;
import com.api.sistemaaprendizajeback.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;

    public AuthServiceImpl(EstudianteRepository estudianteRepository, UsuarioRepository usuarioRepository) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Object authenticate(String username, String password) {

        // Autenticación de estudiante
        Optional<Estudiante> estudianteOpt = estudianteRepository.findByUsernameAndContrasena(username, password);
        if (estudianteOpt.isPresent()) {
            return convertirADTO(estudianteOpt.get());
        }

        // Autenticación de usuario (docente o administrador)
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsernameAndContrasena(username, password);
        if (usuarioOpt.isPresent()) {
            return convertirADTO(usuarioOpt.get());
        }
        return null;
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
