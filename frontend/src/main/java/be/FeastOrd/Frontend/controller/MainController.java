package be.FeastOrd.Frontend.controller;

import be.FeastOrd.Frontend.service.ApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private Button btnRestaurants;
    
    @FXML
    private Button btnMenus;
    
    @FXML
    private Button btnReservations;

    private ApiService apiService;

    @FXML
    public void initialize() {
        apiService = new ApiService();
    }

    @FXML
    private void showRestaurants() {
        loadView("/fxml/RestaurantView.fxml");
    }

    @FXML
    private void showMenus() {
        loadView("/fxml/MenuView.fxml");
    }

    @FXML
    private void showReservations() {
        loadView("/fxml/ReservationView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            mainPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
