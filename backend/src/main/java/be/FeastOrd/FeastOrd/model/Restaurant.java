package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;

@Entity //pour représenter une table en bd
public class Restaurant{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private int etoiles;
    private String ville;
    private String rue;
    private int codePostal;
}