package be.feastord.feastord.frontend.dto;

import java.time.LocalDate;

public class ReservationRequestDto {

    private Integer clientId;
    private Integer restaurantId;
    private Integer menuId;
    private LocalDate date;
    private int nombreClient;
    private String commentaire;
    private String heure;


    public ReservationRequestDto(Integer clientId,
                                 Integer restaurantId,
                                 Integer menuId,
                                 LocalDate date,
                                 int nombreClient,
                                 String commentaire, String heure) {
        this.clientId = clientId;
        this.restaurantId = restaurantId;
        this.menuId = menuId;
        this.date = date;
        this.nombreClient = nombreClient;
        this.commentaire = commentaire;
        this.heure = heure;
    }

    // ✅ GETTERS OBLIGATOIRES POUR JACKSON

    public Integer getClientId() {
        return clientId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNombreClient() {
        return nombreClient;
    }

    public String getCommentaire() {
        return commentaire;
    }
    public String getHeure() {
        return heure;
    }

}
