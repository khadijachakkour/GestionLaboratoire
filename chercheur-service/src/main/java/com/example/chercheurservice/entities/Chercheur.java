package com.example.chercheurservice.entities;

import jakarta.persistence.*;
import com.example.chercheurservice.model.Enseignant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Chercheur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String numeroInscription;

    private String Password;
    private Long id_e;
    private Long projetId;

    private String username;
    @Transient
    private Enseignant Enseignant;

}
