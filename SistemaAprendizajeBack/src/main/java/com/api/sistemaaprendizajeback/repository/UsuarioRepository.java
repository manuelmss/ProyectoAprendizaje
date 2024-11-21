package com.api.sistemaaprendizajeback.repository;

import com.api.sistemaaprendizajeback.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRolNombreRol(String nombreRol);
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
    boolean existsByCorreoElectronico(String correoElectronico);
    boolean existsByUsername(String username);
    boolean existsByDni(String dni);
    Page<Usuario> findByRolId(Long rolId, Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE " +
            "(:rolId IS NULL OR u.rol.id = :rolId) AND " +
            "(COALESCE(:search, '') = '' OR " +
            "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.correoElectronico) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "u.numeroTelefono LIKE CONCAT('%', :search, '%') OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "u.dni LIKE CONCAT('%', :search, '%'))")
    Page<Usuario> findByRolIdAndSearch(Long rolId, String search, Pageable pageable);
    Optional<Usuario> findByUsernameAndContrasena(String username, String contrasena);
}
