package be.FeastOrd.FeastOrd.service;
import be.FeastOrd.FeastOrd.model.Utilisateur;
import be.FeastOrd.FeastOrd.model.TypeRole;
import be.FeastOrd.FeastOrd.model.Role;
import be.FeastOrd.FeastOrd.repository.UtilisateurRepository;
import be.FeastOrd.FeastOrd.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private RoleRepository roleRepository; 


    public Utilisateur inscrireUtilisateur(String nom, String prenom, String mail, String motDePasse, TypeRole typeRole) {
        

        if (utilisateurRepository.findByMail(mail).isPresent()) {
            throw new IllegalArgumentException("Un compte avec ce mail existe déjà.");
        }
        Utilisateur nouvelUtilisateur = new Utilisateur(nom, prenom, mail, motDePasse);
        Optional<Role> roleOpt = roleRepository.findByRole(typeRole);
        if (!roleOpt.isPresent()) {
            throw new RuntimeException("Rôle non trouvé en base de données : " + typeRole);
        }
        nouvelUtilisateur.addRole(roleOpt.get());
        return utilisateurRepository.save(nouvelUtilisateur);
    }
    
    public Utilisateur connecterUtilisateur(String mail, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepository.findByMail(mail).orElse(null);

        if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) {
            return utilisateur; 
        }
        return null;
    }
}