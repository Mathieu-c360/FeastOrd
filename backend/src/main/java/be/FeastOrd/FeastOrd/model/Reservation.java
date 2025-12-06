package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Reservation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    private int nombreClient;
    private String commentaire;
    @ManyToOne 
    @JoinColumn(name = "menu_id") // Clé étrangère dans la table Reservation
    private Menu menuClient;
    
    @Enumerated(EnumType.STRING)
    private EtatReservation etat; 
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    public Reservation() {}
    public Reservation(int nombreClient,String commentaire)
    {
        this.nombreClient=nombreClient;
        this.commentaire=commentaire;
    }
    public int getNombreClient()
    {
        return nombreClient;
    }
    public String getCommentaire()
    {
        return commentaire;
    }
}