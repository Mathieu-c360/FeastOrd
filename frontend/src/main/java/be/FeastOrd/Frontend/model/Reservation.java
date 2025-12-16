package be.FeastOrd.Frontend.model;

import java.util.Date;

public class Reservation {
    private int id;
    private int nombreClient;
    private String nomClient;
    private Menu menuClient;
    private String commentaire;
    private String etat;
    private Date date;

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNombreClient() {
        return nombreClient;
    }

    public void setNombreClient(int nombreClient) {
        this.nombreClient = nombreClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public Menu getMenuClient() {
        return menuClient;
    }

    public void setMenuClient(Menu menuClient) {
        this.menuClient = menuClient;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return nomClient + " - " + nombreClient + " personne(s) - " + etat;
    }
}
