package be.FeastOrd.Frontend.model;

public class Restaurant {
    private int id;
    private String nom;
    private int etoiles;
    private String ville;
    private String rue;
    private int codePostal;

    public Restaurant() {
    }

    public Restaurant(int id, String nom, int etoiles, String ville, String rue, int codePostal) {
        this.id = id;
        this.nom = nom;
        this.etoiles = etoiles;
        this.ville = ville;
        this.rue = rue;
        this.codePostal = codePostal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEtoiles() {
        return etoiles;
    }

    public void setEtoiles(int etoiles) {
        this.etoiles = etoiles;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getAdresseComplete() {
        return rue + ", " + codePostal + " " + ville;
    }

    @Override
    public String toString() {
        return nom + " (" + etoiles + " étoiles) - " + ville;
    }
}
