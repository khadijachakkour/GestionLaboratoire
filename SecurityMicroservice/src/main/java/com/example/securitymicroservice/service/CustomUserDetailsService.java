package com.example.securitymicroservice.service;
import com.example.securitymicroservice.model.Chercheur;
import com.example.securitymicroservice.model.Enseignant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String chercheurUrl = "http://localhost:8082/api/chercheurs/username/" + username;
        String enseignantUrl = "http://localhost:8081/api/enseignants/username/" + username;

        // Vérifier si l'utilisateur est un chercheur
        try {
            System.out.println("Requête vers chercheur service: " + chercheurUrl);
            ResponseEntity<Chercheur> chercheurResponse = restTemplate.getForEntity(chercheurUrl, Chercheur.class);
            System.out.println("Réponse du service chercheur: " + chercheurResponse);
            if (chercheurResponse.getStatusCode() == HttpStatus.OK) {
                Chercheur chercheur = chercheurResponse.getBody();
                // Retourner l'utilisateur avec le mot de passe haché
                return org.springframework.security.core.userdetails.User
                        .withUsername(chercheur.getNumeroInscription())
                        .password(chercheur.getPassword())
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération du chercheur: " + e.getMessage());
        }

        // Vérifier si l'utilisateur est un enseignant
        try {
            System.out.println("Requête vers enseignant service: " + enseignantUrl);
            ResponseEntity<Enseignant> enseignantResponse = restTemplate.getForEntity(enseignantUrl, Enseignant.class);
            System.out.println("Réponse du service enseignant: " + enseignantResponse);
            if (enseignantResponse.getStatusCode() == HttpStatus.OK) {
                Enseignant enseignant = enseignantResponse.getBody();
                return org.springframework.security.core.userdetails.User
                        .withUsername(enseignant.getUsername())
                        .password(enseignant.getMotDePasse())
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de l'enseignant: " + e.getMessage());
        }

        throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
    }

}
