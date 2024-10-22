package com.example.projetservice.repository;

import com.example.projetservice.entities.projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<projet, Long> {
    List<projet> findByChercheurId(Long chercheurId);
}
