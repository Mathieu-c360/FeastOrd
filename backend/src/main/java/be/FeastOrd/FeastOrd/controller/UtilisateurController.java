package be.FeastOrd.FeastOrd.controller;
import be.FeastOrd.FeastOrd.model.Utilisateur;
import be.FeastOrd.FeastOrd.model.TypeRole;
import be.FeastOrd.FeastOrd.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller 
public class UtilisateurController {
    
    @Autowired
    private UtilisateurService utilisateurService;


    public Utilisateur creerCompteClient(String nom, String prenom, String mail, String motDePasse) {
        return utilisateurService.inscrireUtilisateur(nom, prenom, mail, motDePasse, TypeRole.CLIENT);
    }


    public Utilisateur creerCompteGestionnaire(String nom, String prenom, String mail, String motDePasse) {
        return utilisateurService.inscrireUtilisateur(nom, prenom, mail, motDePasse, TypeRole.GESTIONNAIRE);
    }
    

    public Utilisateur connecter(String mail, String motDePasse) {
        return utilisateurService.connecterUtilisateur(mail, motDePasse);
    }
}
