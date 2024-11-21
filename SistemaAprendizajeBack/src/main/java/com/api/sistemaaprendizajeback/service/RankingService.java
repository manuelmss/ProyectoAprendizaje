package com.api.sistemaaprendizajeback.service;


import com.api.sistemaaprendizajeback.model.Ranking;
import java.util.List;
import java.util.Optional;

public interface RankingService {

    Ranking agregarRanking(Ranking ranking);
    List<Ranking> obtenerRankingsPorEstudiante(Long estudianteId);
    List<Ranking> obtenerRankingsPorTema(Long temaId);
    List<Ranking> obtenerRankingsPorDificultad(String dificultad);
    List<Ranking> obtenerTodosLosRankings();


}
