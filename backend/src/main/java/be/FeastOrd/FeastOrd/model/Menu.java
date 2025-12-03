package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;
@Entity //pour représenter une table en bd
public class Menu{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Repas entree;
    private Repas repas;
    private Repas dessert;
}