package be.FeastOrd.FeastOrd.dto;


import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequest {

    private int menuId;
    private Date date;
    private String nomClient;
    private int nombreClient;
    private String commentaire;

}
