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
}