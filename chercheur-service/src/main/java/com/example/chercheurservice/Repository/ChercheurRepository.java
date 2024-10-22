package com.example.chercheurservice.Repository;

import com.example.chercheurservice.entities.Chercheur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChercheurRepository extends JpaRepository<Chercheur,Long> {

    Chercheur findByNumeroInscription(String numeroInscription);
    Chercheur findByUsername(String username);


}
