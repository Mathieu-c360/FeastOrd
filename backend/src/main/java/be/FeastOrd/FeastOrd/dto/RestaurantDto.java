package be.FeastOrd.FeastOrd.dto;

import lombok.Data;

@Data
public class RestaurantDto {

    private String nom;
    private int etoiles;
    private String ville;
    private String rue;
    private int codePostal;
}
