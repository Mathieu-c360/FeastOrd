package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;
import java.util.Date;
@Entity //pour représenter une table en bd
public class Reservation{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int nombreClient;
    private Menu menuClient;
    private String commentaire;
    private EtatReservation etat;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}