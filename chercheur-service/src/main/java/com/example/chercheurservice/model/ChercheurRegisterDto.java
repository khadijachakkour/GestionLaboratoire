package com.example.chercheurservice.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChercheurRegisterDto {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le numéro d'inscription est obligatoire")
    private String numeroInscription;

    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String Password;


    private Long id_e;
    private String username;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    public Long getId_e() {
        return id_e;
    }

    public void setId_e(Long id_e) {
        this.id_e = id_e;
    }



    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroInscription() {
        return numeroInscription;
    }

    public void setNumeroInscription(String numeroInscription) {
        this.numeroInscription = numeroInscription;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}

