package be.FeastOrd.FeastOrd.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //pour représenter une table en bd
public class Reservation{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int nombreClient;

    private String nomClient;  

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Menu menuClient;

    private String commentaire;

    @Enumerated(EnumType.STRING)  
    private EtatReservation etat;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    // ==========================================
    // SOLUTION DE CONTOURNEMENT POUR JAVA 25
    // Lombok ne génère pas correctement les getters/setters avec Java 25
    // Ces méthodes sont nécessaires uniquement pour Java 25
    // À supprimer quand Lombok supportera Java 25 ou si vous utilisez Java 21
    // ==========================================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getNombreClient() { return nombreClient; }
    public void setNombreClient(int nombreClient) { this.nombreClient = nombreClient; }
    
    public String getNomClient() { return nomClient; }
    public void setNomClient(String nomClient) { this.nomClient = nomClient; }
    
    public Menu getMenuClient() { return menuClient; }
    public void setMenuClient(Menu menuClient) { this.menuClient = menuClient; }
    
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    
    public EtatReservation getEtat() { return etat; }
    public void setEtat(EtatReservation etat) { this.etat = etat; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}