package com.api.sistemaaprendizajeback.controller;

import com.api.sistemaaprendizajeback.model.Ranking;
import com.api.sistemaaprendizajeback.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
@CrossOrigin(origins = "*")
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    // Ruta para agregar un nuevo ranking
    @PostMapping
    public ResponseEntity<Ranking> agregarRanking(@RequestBody Ranking ranking) {
        Ranking nuevoRanking = rankingService.agregarRanking(ranking);
        return new ResponseEntity<>(nuevoRanking, HttpStatus.CREATED);
    }

    // Ruta para listar rankings por estudiante
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Ranking>> obtenerRankingsPorEstudiante(@PathVariable Long estudianteId) {
        List<Ranking> rankings = rankingService.obtenerRankingsPorEstudiante(estudianteId);
        return new ResponseEntity<>(rankings, HttpStatus.OK);
    }

    // Ruta para listar rankings por tema
    @GetMapping("/tema/{temaId}")
    public ResponseEntity<List<Ranking>> obtenerRankingsPorTema(@PathVariable Long temaId) {
        List<Ranking> rankings = rankingService.obtenerRankingsPorTema(temaId);
        return new ResponseEntity<>(rankings, HttpStatus.OK);
    }

    // Ruta para listar rankings por dificultad
    @GetMapping("/dificultad/{dificultad}")
    public ResponseEntity<List<Ranking>> obtenerRankingsPorDificultad(@PathVariable String dificultad) {
        List<Ranking> rankings = rankingService.obtenerRankingsPorDificultad(dificultad);
        return new ResponseEntity<>(rankings, HttpStatus.OK);
    }

    // Ruta para listar todos los rankings
    @GetMapping
    public ResponseEntity<List<Ranking>> obtenerTodosLosRankings() {
        List<Ranking> rankings = rankingService.obtenerTodosLosRankings();
        return new ResponseEntity<>(rankings, HttpStatus.OK);
    }
}
