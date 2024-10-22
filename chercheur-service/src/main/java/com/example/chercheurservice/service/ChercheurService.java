package com.example.chercheurservice.service;

import com.example.chercheurservice.Repository.ChercheurRepository;
import com.example.chercheurservice.entities.Chercheur;
import com.example.chercheurservice.model.ChercheurRegisterDto;
import com.example.chercheurservice.model.Enseignant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChercheurService {

    private final ChercheurRepository chercheurRepository;
    private final RestTemplate restTemplate;

    public ChercheurService(ChercheurRepository chercheurRepository) {
        this.chercheurRepository = chercheurRepository;
        this.restTemplate = new RestTemplate();
    }

    public Chercheur registerChercheur(ChercheurRegisterDto dto) {
        // Hacher le mot de passe avant de le stocker
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        // Vérifiez si le chercheur existe déjà
        if (chercheurRepository.findByNumeroInscription(dto.getNumeroInscription()) != null) {
            throw new IllegalArgumentException("Un chercheur avec ce numéro d'inscription existe déjà.");
        }

        Chercheur chercheur = new Chercheur();
        chercheur.setNom(dto.getNom());
        chercheur.setPrenom(dto.getPrenom());
        chercheur.setNumeroInscription(dto.getNumeroInscription());
        chercheur.setPassword(hashedPassword); // Stocker le mot de passe haché
        chercheur.setUsername(dto.getUsername());
        chercheur.setId_e(dto.getId_e());

        return chercheurRepository.save(chercheur);
    }

    public Chercheur CreateChercheur(Chercheur c) {
        return chercheurRepository.save(c);
    }

    public List<Chercheur> GetALLChercheur() {
        List<Chercheur> chercheurList = chercheurRepository.findAll();
        if (chercheurList != null) {
            for (Chercheur c : chercheurList) {
                Enseignant e = restTemplate.getForObject("http://localhost:8081/api/enseignants/" + c.getId_e(), Enseignant.class);
                c.setEnseignant(e);
            }
        }
        return chercheurList;
    }

    public Chercheur GetChercheurByID(Long id) {
        Chercheur chercheur = chercheurRepository.findById(id).orElse(null);
        if (chercheur != null) {
            chercheur.setEnseignant(restTemplate.getForObject("http://localhost:8081/api/enseignants/" + chercheur.getId_e(), Enseignant.class));
        }
        return chercheur;
    }

    public Chercheur GetChercheurByUsername(String username) {
        return chercheurRepository.findByUsername(username);
    }

    public Chercheur UpdateChercheur(Long id, Chercheur c) {
        return chercheurRepository.findById(id).map(chercheur -> {
            chercheur.setNom(c.getNom());
            chercheur.setPrenom(c.getPrenom());
            chercheur.setNumeroInscription(c.getNumeroInscription());
            chercheur.setId_e(c.getId_e());
            return chercheurRepository.save(chercheur);
        }).orElseThrow(() -> new RuntimeException("Chercheur non trouvé avec l'ID : " + id));
    }

    public void DeleteChercheur(Long id) {
        chercheurRepository.deleteById(id);
    }
}
