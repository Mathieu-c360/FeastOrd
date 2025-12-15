package be.FeastOrd.FeastOrd.dto;

import be.FeastOrd.FeastOrd.model.EtatReservation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {

    private int nombreClient;

    // utilisateur connecté (SessionManager côté frontend)
    private int clientId;

    private int menuId;

    private String commentaire;

    // initialisé côté backend à ATTENTE
    private EtatReservation etat;

    // date choisie par le client
    private LocalDate date;

    private String heure;
}
