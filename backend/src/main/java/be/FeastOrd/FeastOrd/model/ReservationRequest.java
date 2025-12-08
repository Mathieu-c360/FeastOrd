package be.FeastOrd.FeastOrd.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequest {

    private int menuId;
    private Date date;
    private String nomClient;
    private int nombreClient;
    private String commentaire;
    @Enumerated(EnumType.STRING)
    private EtatReservation etat;

}
