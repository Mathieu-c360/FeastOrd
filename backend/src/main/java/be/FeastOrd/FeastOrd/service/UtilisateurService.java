package be.FeastOrd.FeastOrd.service;
import be.FeastOrd.FeastOrd.model.Utilisateur;
import be.FeastOrd.FeastOrd.model.TypeRole;
import be.FeastOrd.FeastOrd.model.Role;
import be.FeastOrd.FeastOrd.repository.UtilisateurRepository;
import be.FeastOrd.FeastOrd.repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository; 
    public UtilisateurService(UtilisateurRepository utilisateurRepository,RoleRepository roleRepository)
    {
        this.utilisateurRepository=utilisateurRepository;
        this.roleRepository=roleRepository;
    }


    public Utilisateur inscrireUtilisateur(String nom, String prenom, String mail, String motDePasse, TypeRole typeRole) {
        

        if (utilisateurRepository.findByMail(mail).isPresent()) {
            throw new IllegalArgumentException("Un compte avec ce mail existe déjà.");
        }
        Utilisateur nouvelUtilisateur = new Utilisateur(nom, prenom, mail, motDePasse);
        Role role = roleRepository.findByRole(typeRole)
            .orElseThrow(() -> new RuntimeException("Rôle non trouvé : " + typeRole));
        nouvelUtilisateur.setRole(role);
        return utilisateurRepository.save(nouvelUtilisateur); //pour enregistrer définitivement le rôle
    }
    
    public Utilisateur connecterUtilisateur(String mail, String motDePasse) 
    {
        Utilisateur utilisateur = utilisateurRepository.findByMail(mail).orElse(null);

        if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) 
            {
            return utilisateur; 
        }
        return null;
    }
}