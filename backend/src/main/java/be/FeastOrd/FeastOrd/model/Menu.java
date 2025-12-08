package be.FeastOrd.FeastOrd.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //pour représenter une table en bd
public class Menu{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Repas entree;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Repas repas;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Repas dessert;




     // ==========================================
    // SOLUTION DE CONTOURNEMENT POUR JAVA 25
    // Lombok ne génère pas correctement les getters/setters avec Java 25
    // Ces méthodes sont nécessaires uniquement pour Java 25
    // À supprimer quand Lombok supportera Java 25 ou si vous utilisez Java 21
    // ==========================================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
    
    public Repas getEntree() { return entree; }
    public void setEntree(Repas entree) { this.entree = entree; }
    
    public Repas getRepas() { return repas; }
    public void setRepas(Repas repas) { this.repas = repas; }
    
    public Repas getDessert() { return dessert; }
    public void setDessert(Repas dessert) { this.dessert = dessert; }
}