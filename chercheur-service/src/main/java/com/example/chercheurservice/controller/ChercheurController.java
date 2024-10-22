package com.example.chercheurservice.controller;

import com.example.chercheurservice.entities.Chercheur;
import com.example.chercheurservice.model.ChercheurRegisterDto;
import com.example.chercheurservice.service.ChercheurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chercheurs")
public class ChercheurController {

    @Autowired
    private ChercheurService chercheurService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ChercheurRegisterDto chercheurRegisterDto) {
        chercheurService.registerChercheur(chercheurRegisterDto);
        return ResponseEntity.ok("Utilisateur enregistré avec succès");
    }

    @PostMapping
    public ResponseEntity<Chercheur> createChercheur(@RequestBody Chercheur chercheur) {
        Chercheur newChercheur = chercheurService.CreateChercheur(chercheur);
        return ResponseEntity.ok(newChercheur);
    }

    @GetMapping
    public ResponseEntity<List<Chercheur>> getAllChercheurs() {
        List<Chercheur> chercheurList = chercheurService.GetALLChercheur();
        return ResponseEntity.ok(chercheurList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chercheur> getChercheurById(@PathVariable Long id) {
        Chercheur chercheur = chercheurService.GetChercheurByID(id);
        return (chercheur != null) ? ResponseEntity.ok(chercheur) : ResponseEntity.notFound().build();
    }

    // Récupérer un chercheur par username
    @GetMapping("/username/{username}")
    public ResponseEntity<Chercheur> getChercheurByUsername(@PathVariable String username) {
        Chercheur chercheur = chercheurService.GetChercheurByUsername(username);
        return (chercheur != null) ? ResponseEntity.ok(chercheur) : ResponseEntity.notFound().build();
    }

    // Mise à jour d'un chercheur
    @PutMapping("/{id}")
    public ResponseEntity<Chercheur> updateChercheur(@PathVariable Long id, @RequestBody Chercheur chercheur) {
        Chercheur updatedChercheur = chercheurService.UpdateChercheur(id, chercheur);
        return ResponseEntity.ok(updatedChercheur);
    }

    // Suppression d'un chercheur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChercheur(@PathVariable Long id) {
        chercheurService.DeleteChercheur(id);
        return ResponseEntity.noContent().build();
    }
}
