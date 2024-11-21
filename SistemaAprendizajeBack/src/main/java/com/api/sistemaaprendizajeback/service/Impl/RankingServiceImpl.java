package com.api.sistemaaprendizajeback.service.impl;

import com.api.sistemaaprendizajeback.model.Ranking;
import com.api.sistemaaprendizajeback.repository.RankingRepository;
import com.api.sistemaaprendizajeback.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;

    @Autowired
    public RankingServiceImpl(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public Ranking agregarRanking(Ranking ranking) {
        Optional<Ranking> rankingExistente = rankingRepository.findByEstudianteIdAndTemaIdAndDificultad(
                ranking.getEstudiante().getId(),
                ranking.getTema().getId(),
                ranking.getDificultad()
        );

        if (rankingExistente.isPresent()) {
            Ranking rankingActualizado = rankingExistente.get();
            rankingActualizado.setPuntaje(ranking.getPuntaje());
            rankingActualizado.setTrofeo(ranking.getTrofeo());
            rankingActualizado.setFechaRegistro(LocalDateTime.now());
            return rankingRepository.save(rankingActualizado);
        } else {
            // Si no existe, guardar el nuevo ranking
            ranking.setFechaRegistro(LocalDateTime.now());
            return rankingRepository.save(ranking);
        }
    }

    @Override
    public List<Ranking> obtenerRankingsPorEstudiante(Long estudianteId) {
        return rankingRepository.findByEstudianteId(estudianteId);
    }

    @Override
    public List<Ranking> obtenerRankingsPorTema(Long temaId) {
        return rankingRepository.findByTemaId(temaId);
    }

    @Override
    public List<Ranking> obtenerRankingsPorDificultad(String dificultad) {
        return rankingRepository.findByDificultad(dificultad);
    }

    @Override
    public List<Ranking> obtenerTodosLosRankings() {
        return rankingRepository.findAll();
    }
}
