package com.example.securitymicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enseignant {

    private String nom;
    private String prenom;
    private String cne;
    private String email;
    private String motDePasse;
    private String thematiqueRecherche;

    private String username;
}
