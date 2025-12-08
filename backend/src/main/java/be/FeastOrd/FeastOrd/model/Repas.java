package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;
@Entity //pour représenter une table en bd
public class Repas{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private double prix;

    @Enumerated(EnumType.STRING)
    private TypeRepas type;


    // Getters et setters manuels (pour Java 25 + compatibilité JSON)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public TypeRepas getType() { return type; }
    public void setType(TypeRepas type) { this.type = type; }


}