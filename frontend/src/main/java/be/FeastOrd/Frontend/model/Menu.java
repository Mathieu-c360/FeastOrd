package be.FeastOrd.Frontend.model;

public class Menu {
    private int id;
    private Restaurant restaurant;
    private Repas entree;
    private Repas repas;
    private Repas dessert;

    public Menu() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Repas getEntree() {
        return entree;
    }

    public void setEntree(Repas entree) {
        this.entree = entree;
    }

    public Repas getRepas() {
        return repas;
    }

    public void setRepas(Repas repas) {
        this.repas = repas;
    }

    public Repas getDessert() {
        return dessert;
    }

    public void setDessert(Repas dessert) {
        this.dessert = dessert;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (entree != null) sb.append("Entrée: ").append(entree.getNom()).append("\n");
        if (repas != null) sb.append("Plat: ").append(repas.getNom()).append("\n");
        if (dessert != null) sb.append("Dessert: ").append(dessert.getNom());
        return sb.toString();
    }
}
