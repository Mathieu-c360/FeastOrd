package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.model.Utilisateur;
import be.FeastOrd.FeastOrd.model.TypeRole;
import be.FeastOrd.FeastOrd.service.UtilisateurService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs") 
public class UtilisateurController {
    
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService)
    {
        this.utilisateurService = utilisateurService;
    }
    public static class InscriptionRequest {
        @NotBlank(message = "Le nom est obligatoire") //empêcher que ça soit null
        public String nom;

        @NotBlank(message = "Le prénom est obligatoire")
        public String prenom;

        @NotBlank(message = "L'email est obligatoire")
        @Pattern( //c'est comme une règle à suivre
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", 
            message = "Le format de l'email est invalide"
        )
        public String mail;

        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 4, message = "Le mot de passe doit contenir au moins 4 caractères") //oblige une taille de caractères minimum
        public String motDePasse;
    }
    
    public static class LoginRequest {
        @NotBlank(message = "Le mail est obligatoire")
        public String mail;

        @NotBlank(message = "Le mot de passe est obligatoire")
        public String motDePasse;
    }

    @PostMapping("/register/client")
    public ResponseEntity<?> creerCompteClient(@Valid @RequestBody InscriptionRequest request) //RequestBody pour traduire le json en objet
    { //le @Valid dit au controller de vérifier avant de créer
        try 
        {
            Utilisateur u = utilisateurService.inscrireUtilisateur(request.nom, request.prenom, request.mail, request.motDePasse, TypeRole.CLIENT);
            return ResponseEntity.ok(u);
        } 
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/gestionnaire")
    public ResponseEntity<?> creerCompteGestionnaire(@Valid @RequestBody InscriptionRequest request) 
    {
        try 
        {
            Utilisateur u = utilisateurService.inscrireUtilisateur(request.nom, request.prenom, request.mail, request.motDePasse, TypeRole.GESTIONNAIRE);
            return ResponseEntity.ok(u);
        } 
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> connecter(@Valid @RequestBody LoginRequest request)
    {
        Utilisateur u = utilisateurService.connecterUtilisateur(request.mail, request.motDePasse);
        if (u != null) 
        {
            return ResponseEntity.ok(u);
        } 
        else 
        {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }
}