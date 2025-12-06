package be.FeastOrd.FeastOrd.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
@Entity //pour représenter une table en bd
public class Utilisateur{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String motDePasse;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "utilisateur_role", 
        joinColumns = @JoinColumn(name = "utilisateur_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    public Utilisateur(){}
    public Utilisateur(String nom,String prenom,String mail,String motDePasse)
    {
        this.nom=nom;
        this.prenom=prenom;
        this.mail=mail;
        this.motDePasse=motDePasse;
    }
    public String getNom()
    {
        return nom;
    }
    public String getPrenom()
    {
        return prenom;
    }
    public String getMail()
    {
        return mail;
    }
    public String getMotDePasse()
    {
        return motDePasse;
    }
    public void addRole(Role role) {
        this.roles.add(role);
    }
}