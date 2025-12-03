package be.feastord.feastord.frontend.dto;

import java.util.Date;

public class ReservationRequestFX {

    private int menuId;
    private String nomClient;
    private int nombreClient;
    private String commentaire;
    private Date date;

    public ReservationRequestFX(int menuId, String nomClient, int nombreClient, String commentaire, Date date) {
        this.menuId = menuId;
        this.nomClient = nomClient;
        this.nombreClient = nombreClient;
        this.commentaire = commentaire;
        this.date = date;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getNomClient() {
        return nomClient;
    }

    public int getNombreClient() {
        return nombreClient;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Date getDate() {
        return date;
    }
}
