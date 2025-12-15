package be.feastord.feastord.frontend.dto;



public class MenuDto {

    private int id;
    private RepasDto entree;
    private RepasDto repas;   // ✅ même nom que le backend
    private RepasDto dessert;
    private RestaurantDto restaurant;

    public int getId() {
        return id;
    }

    public RepasDto getEntree() {
        return entree;
    }

    public RepasDto getRepas() {   // ✅
        return repas;
    }

    public RepasDto getDessert() {
        return dessert;
    }
    public RestaurantDto getRestaurant() {
        return restaurant;
    }
}
