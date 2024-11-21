package com.api.sistemaaprendizajeback.repository;

import com.api.sistemaaprendizajeback.model.Dificultad;
import com.api.sistemaaprendizajeback.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findByEstudianteId(Long estudianteId);
    List<Ranking> findByTemaId(Long temaId);
    List<Ranking> findByDificultad(String dificultad);
    Optional<Ranking> findByEstudianteIdAndTemaId(Long estudianteId, Long temaId);
    Optional<Ranking> findByEstudianteIdAndTemaIdAndDificultad(Long estudianteId, Long temaId, Dificultad dificultad);

}
