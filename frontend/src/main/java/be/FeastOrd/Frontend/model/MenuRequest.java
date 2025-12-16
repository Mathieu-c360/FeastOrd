package be.FeastOrd.Frontend.model;

public class MenuRequest {
    private Integer restaurantId;
    private Integer entreeId;
    private Integer repasId;
    private Integer dessertId;

    public MenuRequest() {
    }

    public MenuRequest(Integer restaurantId, Integer entreeId, Integer repasId, Integer dessertId) {
        this.restaurantId = restaurantId;
        this.entreeId = entreeId;
        this.repasId = repasId;
        this.dessertId = dessertId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getEntreeId() {
        return entreeId;
    }

    public void setEntreeId(Integer entreeId) {
        this.entreeId = entreeId;
    }

    public Integer getRepasId() {
        return repasId;
    }

    public void setRepasId(Integer repasId) {
        this.repasId = repasId;
    }

    public Integer getDessertId() {
        return dessertId;
    }

    public void setDessertId(Integer dessertId) {
        this.dessertId = dessertId;
    }
}
