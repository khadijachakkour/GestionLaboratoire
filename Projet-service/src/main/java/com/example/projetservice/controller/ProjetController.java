package com.example.projetservice.controller;

import com.example.projetservice.entities.projet;
import com.example.projetservice.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {
    @Autowired
    private ProjetService projetService;

    @PostMapping
    public ResponseEntity<projet> creerProjet(@RequestBody projet projet) {
        return ResponseEntity.ok(projetService.creerProjet(projet));
    }

    @GetMapping("/chercheur/{chercheurId}")
    public ResponseEntity<List<projet>> getProjetsByChercheur(@PathVariable Long chercheurId) {
        return ResponseEntity.ok(projetService.getProjetsByChercheur(chercheurId));
    }
}

