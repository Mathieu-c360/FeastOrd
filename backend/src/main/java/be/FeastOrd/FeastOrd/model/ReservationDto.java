package be.FeastOrd.FeastOrd.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;



@Data
public class ReservationDto {
    private int nombreClient;

    private String commentaire;

    @Enumerated(EnumType.STRING)
    private EtatReservation etat;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
