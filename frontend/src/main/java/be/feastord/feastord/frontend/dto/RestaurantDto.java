package be.feastord.feastord.frontend.dto;

public class RestaurantDto {

    private int id;
    private String nom;
    private int etoiles;
    private String ville;
    private String rue;
    private int codePostal;

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getEtoiles() {
        return etoiles;
    }

    public String getVille() {
        return ville;
    }

    public String getRue() {
        return rue;
    }

    public int getCodePostal() {
        return codePostal;
    }
}
