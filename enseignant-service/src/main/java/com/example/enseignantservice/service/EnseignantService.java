package com.example.enseignantservice.service;

import com.example.enseignantservice.entities.Enseignant;
import com.example.enseignantservice.repository.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id).orElse(null);
    }

    public Enseignant addEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }

    public Enseignant UpdateEnseignant(Long id, Enseignant e){
        return enseignantRepository.findById(id).map(enseignant ->{
            enseignant.setCne(e.getCne());
            enseignant.setNom(e.getNom());
            enseignant.setEmail(e.getEmail());
            enseignant.setPrenom(e.getPrenom());
            enseignant.setMotDePasse(e.getMotDePasse());
            enseignant.setThematiqueRecherche(e.getThematiqueRecherche());
            return  enseignantRepository.save(enseignant);
        }).orElseThrow(() -> new RuntimeException("non trouvé"));
    }
}

