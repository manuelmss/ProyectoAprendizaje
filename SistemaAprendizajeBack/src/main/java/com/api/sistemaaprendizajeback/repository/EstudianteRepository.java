package com.api.sistemaaprendizajeback.repository;

import com.api.sistemaaprendizajeback.model.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findById(Long usuarioId);
    Page<Estudiante> findByGradoId(Long gradoId, Pageable pageable);

    @Query("SELECT e FROM Estudiante e WHERE " +
            "(:gradoId IS NULL OR e.grado.id = :gradoId) AND " +
            "(COALESCE(:search, '') = '' OR " +
            "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.correoElectronico) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "e.numeroTelefono LIKE CONCAT('%', :search, '%') OR " +
            "LOWER(e.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "e.dni LIKE CONCAT('%', :search, '%'))")
    Page<Estudiante> findByGradoIdAndSearch(Long gradoId, String search, Pageable pageable);

    boolean existsByDni(String dni);
    boolean existsByCorreoElectronico(String correoElectronico);
    boolean existsByUsername(String username);
    Optional<Estudiante> findByUsernameAndContrasena(String username, String contrasena);
}
