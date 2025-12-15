package be.feastord.feastord.frontend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationDto {

    private int id;
    private String date;
    private String heure;
    private String etat;
    private int nombreClient;
    private String commentaire;
    private MenuDto menuClient;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public String getEtat() {
        return etat;
    }

    public int getNombreClient() {
        return nombreClient;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public MenuDto getMenuClient() {
        return menuClient;
    }

    // 🔧 setters nécessaires

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void setNombreClient(int nombreClient) {
        this.nombreClient = nombreClient;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setMenuClient(MenuDto menuClient) {
        this.menuClient = menuClient;
    }
}
