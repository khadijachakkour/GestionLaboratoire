package com.example.chercheurservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class ChercheurServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChercheurServiceApplication.class, args);
    }


    @Bean
        //Methode pour crypter les mots de passes
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
