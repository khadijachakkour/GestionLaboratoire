package com.example.projetservice.service;

import com.example.projetservice.entities.projet;
import com.example.projetservice.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {
    @Autowired
    private ProjetRepository projetRepository;

    public projet creerProjet(projet projet) {
        return projetRepository.save(projet);
    }

    public List<projet> getProjetsByChercheur(Long chercheurId) {
        return projetRepository.findByChercheurId(chercheurId);
    }
}

