package be.feastord.feastord.frontend.controller;

import be.feastord.feastord.frontend.util.SceneManager;
import javafx.event.ActionEvent;

public class NavBarController {

    public void goToRestaurants(ActionEvent event) {
        SceneManager.switchScene(event, "/views/restaurant-list.fxml");
    }

    public void goToMesReservations(ActionEvent event) {
        SceneManager.switchScene(event, "/views/mes-reservations.fxml");
    }
}
