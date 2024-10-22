package com.example.securitymicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chercheur {

    private Long id;
    private String nom;
    private String prenom;
    private String numeroInscription;

    private String Password;
    private Long id_e;

    private String username;
}
