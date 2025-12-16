package be.FeastOrd.Frontend.controller;

import be.FeastOrd.Frontend.model.Reservation;
import be.FeastOrd.Frontend.model.Restaurant;
import be.FeastOrd.Frontend.service.ApiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ReservationController {
    @FXML
    private TableView<Reservation> reservationTable;
    
    @FXML
    private TableColumn<Reservation, Integer> colId;
    
    @FXML
    private TableColumn<Reservation, String> colNomClient;
    
    @FXML
    private TableColumn<Reservation, Integer> colNombreClient;
    
    @FXML
    private TableColumn<Reservation, String> colEtat;
    
    @FXML
    private TableColumn<Reservation, String> colDate;
    
    @FXML
    private TableColumn<Reservation, String> colCommentaire;
    
    @FXML
    private TableColumn<Reservation, String> colRestaurant;
    
    @FXML
    private ComboBox<Restaurant> comboRestaurant;
    
    @FXML
    private ComboBox<String> comboEtat;
    
    @FXML
    private Button btnAccepter;
    
    @FXML
    private Button btnRefuser;
    
    @FXML
    private Button btnActualiser;
    
    @FXML
    private Button btnFiltrer;

    private ApiService apiService;
    private ObservableList<Reservation> reservations;
    private ObservableList<Restaurant> restaurants;
    private Reservation reservationSelectionnee;

    @FXML
    public void initialize() {
        apiService = new ApiService();
        reservations = FXCollections.observableArrayList();
        restaurants = FXCollections.observableArrayList();
        
        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRestaurant.setCellValueFactory(cellData -> {
            if (cellData.getValue().getMenuClient() != null && 
                cellData.getValue().getMenuClient().getRestaurant() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                    cellData.getValue().getMenuClient().getRestaurant().getNom()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });
        colNomClient.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        colNombreClient.setCellValueFactory(new PropertyValueFactory<>("nombreClient"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colCommentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        colDate.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                return new javafx.beans.property.SimpleStringProperty(
                    sdf.format(cellData.getValue().getDate())
                );
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        
        reservationTable.setItems(reservations);
        reservationTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                reservationSelectionnee = newSelection;
                btnAccepter.setDisable(newSelection == null || 
                    "ACCEPTEE".equals(newSelection.getEtat()) || 
                    "REFUSEE".equals(newSelection.getEtat()));
                btnRefuser.setDisable(newSelection == null || 
                    "ACCEPTEE".equals(newSelection.getEtat()) || 
                    "REFUSEE".equals(newSelection.getEtat()));
            }
        );
        
        // Configuration du combo état
        comboEtat.setItems(FXCollections.observableArrayList(
            "EN_ATTENTE", "ACCEPTEE", "REFUSEE"
        ));
        
        chargerDonnees();
    }

    @FXML
    private void accepterReservation() {
        if (reservationSelectionnee == null) {
            afficherErreur("Erreur", "Veuillez sélectionner une réservation");
            return;
        }
        
        try {
            apiService.accepterReservation(reservationSelectionnee.getId());
            chargerReservations();
            afficherMessage("Succès", "Réservation acceptée avec succès!");
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible d'accepter la réservation: " + e.getMessage());
        }
    }

    @FXML
    private void refuserReservation() {
        if (reservationSelectionnee == null) {
            afficherErreur("Erreur", "Veuillez sélectionner une réservation");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Refuser la réservation");
        alert.setContentText("Êtes-vous sûr de vouloir refuser cette réservation?");
        
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                apiService.refuserReservation(reservationSelectionnee.getId());
                chargerReservations();
                afficherMessage("Succès", "Réservation refusée");
            } catch (IOException e) {
                afficherErreur("Erreur", "Impossible de refuser la réservation: " + e.getMessage());
            }
        }
    }

    @FXML
    private void filtrerReservations() {
        if (comboRestaurant.getValue() == null && comboEtat.getValue() == null) {
            chargerReservations();
            return;
        }
        
        try {
            reservations.clear();
            if (comboRestaurant.getValue() != null && comboEtat.getValue() != null) {
                // Filtrer par restaurant et état
                // Note: Vous devrez peut-être créer cet endpoint dans le backend
                reservations.addAll(apiService.getReservationsByRestaurant(
                    comboRestaurant.getValue().getId()
                ));
                reservations.removeIf(r -> !r.getEtat().equals(comboEtat.getValue()));
            } else if (comboRestaurant.getValue() != null) {
                reservations.addAll(apiService.getReservationsByRestaurant(
                    comboRestaurant.getValue().getId()
                ));
            } else {
                // Filtrer par état uniquement
                reservations.addAll(apiService.getAllReservations());
                reservations.removeIf(r -> !r.getEtat().equals(comboEtat.getValue()));
            }
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible de filtrer les réservations: " + e.getMessage());
        }
    }

    @FXML
    private void actualiserReservations() {
        chargerDonnees();
    }

    private void chargerDonnees() {
        chargerRestaurants();
        chargerReservations();
    }

    private void chargerRestaurants() {
        try {
            restaurants.clear();
            restaurants.addAll(apiService.getAllRestaurants());
            comboRestaurant.setItems(restaurants);
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible de charger les restaurants: " + e.getMessage());
        }
    }

    private void chargerReservations() {
        try {
            reservations.clear();
            reservations.addAll(apiService.getAllReservations());
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible de charger les réservations: " + e.getMessage());
        }
    }

    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
