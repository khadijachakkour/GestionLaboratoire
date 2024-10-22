package com.example.securitymicroservice.configuration;

import com.example.securitymicroservice.service.CustomUserDetailsService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    private final PasswordEncoder passwordEncoder;
    private final RsaConfig rsaConfig; // Contient les configurations liées aux clés RSA
    private final CustomUserDetailsService userDetailsService; // Service pour gérer les utilisateurs

    public SecurityConfig(PasswordEncoder passwordEncoder, RsaConfig rsaConfig, CustomUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.rsaConfig = rsaConfig;
        this.userDetailsService = userDetailsService;
    }

    // Service d'authentification des utilisateurs
    @Bean
    public AuthenticationManager authManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    // Filtre de sécurité pour les requêtes HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // La gestion de session est sans état
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Désactive la protection CSRF (car on doit utiliser JWT)
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers("/register/**", "/login/**", "/RefreshToken/**").permitAll()
                        .anyRequest().authenticated())
                // Configure l'application pour utiliser OAuth2 et JWT
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                // Désactive la page de login envoyée par défaut par Spring Security
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean // Méthode pour signer les tokens
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaConfig.publicKey()).privateKey(rsaConfig.rsaPrivateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource); // Encodeur qui utilise la clé privée pour signer les tokens JWT
    }

    @Bean // Pour vérifier la signature d'un token JWT à l'aide de la clé publique
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaConfig.publicKey()).build();
    }
}
