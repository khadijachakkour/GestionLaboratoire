package com.example.enseignantservice.repository;

import com.example.enseignantservice.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}