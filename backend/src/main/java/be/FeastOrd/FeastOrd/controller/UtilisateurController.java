package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.model.Utilisateur;
import be.FeastOrd.FeastOrd.model.TypeRole;
import be.FeastOrd.FeastOrd.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs") 
public class UtilisateurController {
    
    @Autowired
    private UtilisateurService utilisateurService;

    public static class InscriptionRequest {
        public String nom;
        public String prenom;
        public String mail;
        public String motDePasse;
    }
    
    public static class LoginRequest {
        public String mail;
        public String motDePasse;
    }

    @PostMapping("/register/client")
    public ResponseEntity<?> creerCompteClient(@RequestBody InscriptionRequest request) 
    {
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
    public ResponseEntity<?> creerCompteGestionnaire(@RequestBody InscriptionRequest request) 
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
    public ResponseEntity<?> connecter(@RequestBody LoginRequest request)
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