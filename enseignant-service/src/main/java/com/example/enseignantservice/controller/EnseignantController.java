package com.example.enseignantservice.controller;

import com.example.enseignantservice.entities.Enseignant;
import com.example.enseignantservice.service.EnseignantService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/enseignants")
    @OpenAPIDefinition(
            info = @Info(
                    title = "La Gestion des enseignants",
                    description = "offre les opération pour gérer les enseignants",
                    version = "1.0.0"
            ),
            servers = @Server(
                    url = "http://localhost:8081/"
            )
    )

    public class EnseignantController {

        @Autowired
        private EnseignantService enseignantService;


        @Operation(
                summary = "ajouter nouvel enseignat",
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        required = true,
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Enseignant.class)
                        )
                ),
                responses = {
                        @ApiResponse(responseCode ="200",description = "Bien Ajouter",
                                content = @Content(mediaType = "applaction/json"
                                        ,schema = @Schema(implementation = Enseignant.class))),
                        @ApiResponse(responseCode = "400",description = "mal ajouter")
                }



        )
        @PostMapping
        public ResponseEntity<Enseignant> addEnseignant(@RequestBody Enseignant E){
            Enseignant enseignant = enseignantService.addEnseignant(E);
            return ResponseEntity.ok(enseignant);
        }

        @GetMapping
        public ResponseEntity<List<Enseignant>> getAllEnseignants(){
            List<Enseignant> enseignants = enseignantService.getAllEnseignants();
            return ResponseEntity.ok(enseignants);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Enseignant> getEnseignantById(@PathVariable Long id){
            Enseignant enseignant = enseignantService.getEnseignantById(id);
            return ResponseEntity.ok(enseignant);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Enseignant> update(@PathVariable Long id, @RequestBody Enseignant e){
            Enseignant enseignant = enseignantService.UpdateEnseignant(id,e);
            return ResponseEntity.ok(enseignant);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity Delete(@PathVariable Long id){
            enseignantService.deleteEnseignant(id);
            return ResponseEntity.ok().build();
        }








}

