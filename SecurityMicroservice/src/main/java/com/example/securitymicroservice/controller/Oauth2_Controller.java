package com.example.securitymicroservice.controller;

import com.example.securitymicroservice.model.Chercheur;
import com.example.securitymicroservice.model.Enseignant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Oauth2_Controller {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final RestTemplate restTemplate;

    public Oauth2_Controller(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, RestTemplate restTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        // Vérifier l'authentification
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Générer les tokens (Access token et Refresh Token)
        Instant instant = Instant.now();
        String scopes = authenticate.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));

        // Access token
        JwtClaimsSet jwtClaimsSet_AccessToken = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(authenticate.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(15, ChronoUnit.MINUTES))
                .claim("name", authenticate.getName())
                .claim("scope", scopes)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_AccessToken)).getTokenValue();

        // Refresh token
        JwtClaimsSet jwtClaimsSet_RefreshToken = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(authenticate.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(30, ChronoUnit.MINUTES))
                .claim("name", authenticate.getName())
                .build();

        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_RefreshToken)).getTokenValue();

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("Access_Token", accessToken);
        tokenMap.put("Refresh_Token", refreshToken);
        return tokenMap;
    }

    @PostMapping("/RefreshToken")
    public Map<String, String> refreshToken(@RequestParam String refreshToken) {
        if (refreshToken == null) {
            return Map.of("Message error", "Refresh_Token est necessaire");
        }
        Jwt decoded = jwtDecoder.decode(refreshToken);
        String username = decoded.getSubject();

        // Renouveler l'access token
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet_AccessToken = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(username)
                .issuedAt(instant)
                .expiresAt(instant.plus(15, ChronoUnit.MINUTES))
                .claim("name", username)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_AccessToken)).getTokenValue();

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("Access_Token", accessToken);
        tokenMap.put("Refresh_Token", refreshToken);
        return tokenMap;
    }

    // Méthode pour déterminer le type d'utilisateur
    private String getUserType(String username) {
        String chercheurUrl = "http://chercheur-service/api/chercheurs/" + username;
        String enseignantUrl = "http://enseignant-service/api/enseignants/" + username;

        // Vérifier si l'utilisateur est un chercheur
        try {
            ResponseEntity<Chercheur> chercheurResponse = restTemplate.getForEntity(chercheurUrl, Chercheur.class);
            if (chercheurResponse.getStatusCode() == HttpStatus.OK) {
                return "chercheur";
            }
        } catch (Exception e) {
        }

        // Vérifier si l'utilisateur est un enseignant
        try {
            ResponseEntity<Enseignant> enseignantResponse = restTemplate.getForEntity(enseignantUrl, Enseignant.class);
            if (enseignantResponse.getStatusCode() == HttpStatus.OK) {
                return "enseignant";
            }
        } catch (Exception e) {
        }

        return "inconnu";
    }
}
