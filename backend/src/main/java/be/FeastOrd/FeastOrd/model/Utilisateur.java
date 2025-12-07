package be.FeastOrd.FeastOrd.model;


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
    @ManyToOne 
    @JoinColumn(name = "role_id", nullable = false) // Clé étrangère vers la table Role
    private Role role; // Un seul objet Role
    public Utilisateur(){}
    public Utilisateur(String nom,String prenom,String mail,String motDePasse)
    {
        this.nom=nom;
        this.prenom=prenom;
        this.mail=mail;
        this.motDePasse=motDePasse;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
}