package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Utilisateur client;

    @ManyToOne
    private Menu menuClient;

    private String commentaire;

    @Enumerated(EnumType.STRING)
    private EtatReservation etat;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


}