package be.FeastOrd.Frontend.controller;

import be.FeastOrd.Frontend.model.Restaurant;
import be.FeastOrd.Frontend.service.ApiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class RestaurantController {
    @FXML
    private TableView<Restaurant> restaurantTable;
    
    @FXML
    private TableColumn<Restaurant, Integer> colId;
    
    @FXML
    private TableColumn<Restaurant, String> colNom;
    
    @FXML
    private TableColumn<Restaurant, Integer> colEtoiles;
    
    @FXML
    private TableColumn<Restaurant, String> colVille;
    
    @FXML
    private TableColumn<Restaurant, String> colRue;
    
    @FXML
    private TableColumn<Restaurant, Integer> colCodePostal;
    
    @FXML
    private TextField txtNom;
    
    @FXML
    private TextField txtEtoiles;
    
    @FXML
    private TextField txtVille;
    
    @FXML
    private TextField txtRue;
    
    @FXML
    private TextField txtCodePostal;
    
    @FXML
    private Button btnAjouter;
    
    @FXML
    private Button btnModifier;
    
    @FXML
    private Button btnSupprimer;
    
    @FXML
    private Button btnActualiser;

    private ApiService apiService;
    private ObservableList<Restaurant> restaurants;
    private Restaurant restaurantSelectionne;

    @FXML
    public void initialize() {
        apiService = new ApiService();
        restaurants = FXCollections.observableArrayList();
        
        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEtoiles.setCellValueFactory(new PropertyValueFactory<>("etoiles"));
        colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        colRue.setCellValueFactory(new PropertyValueFactory<>("rue"));
        colCodePostal.setCellValueFactory(new PropertyValueFactory<>("codePostal"));
        
        restaurantTable.setItems(restaurants);
        restaurantTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                restaurantSelectionne = newSelection;
                if (newSelection != null) {
                    remplirFormulaire(newSelection);
                }
            }
        );
        
        chargerRestaurants();
    }

    @FXML
    private void ajouterRestaurant() {
        // Validation des champs
        if (txtNom.getText().trim().isEmpty()) {
            afficherErreur("Erreur de validation", "Le nom du restaurant est obligatoire");
            return;
        }
        if (txtVille.getText().trim().isEmpty()) {
            afficherErreur("Erreur de validation", "La ville est obligatoire");
            return;
        }
        if (txtRue.getText().trim().isEmpty()) {
            afficherErreur("Erreur de validation", "La rue est obligatoire");
            return;
        }
        
        int etoiles;
        int codePostal;
        
        try {
            etoiles = Integer.parseInt(txtEtoiles.getText().trim());
            if (etoiles < 0 || etoiles > 5) {
                afficherErreur("Erreur de validation", "Le nombre d'étoiles doit être entre 0 et 5");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur de validation", "Le nombre d'étoiles doit être un nombre valide");
            return;
        }
        
        try {
            codePostal = Integer.parseInt(txtCodePostal.getText().trim());
        } catch (NumberFormatException e) {
            afficherErreur("Erreur de validation", "Le code postal doit être un nombre valide");
            return;
        }
        
        try {
            Restaurant restaurant = new Restaurant();
            restaurant.setNom(txtNom.getText().trim());
            restaurant.setEtoiles(etoiles);
            restaurant.setVille(txtVille.getText().trim());
            restaurant.setRue(txtRue.getText().trim());
            restaurant.setCodePostal(codePostal);
            
            Restaurant nouveau = apiService.createRestaurant(restaurant);
            restaurants.add(nouveau);
            viderFormulaire();
            afficherMessage("Succès", "Restaurant ajouté avec succès!");
        } catch (IOException e) {
            afficherErreur("Erreur de connexion", "Impossible de se connecter au serveur. Vérifiez que le backend est démarré.\n\nDétails: " + e.getMessage());
        } catch (Exception e) {
            afficherErreur("Erreur", "Impossible d'ajouter le restaurant: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierRestaurant() {
        if (restaurantSelectionne == null) {
            afficherErreur("Erreur", "Veuillez sélectionner un restaurant à modifier");
            return;
        }
        
        try {
            restaurantSelectionne.setNom(txtNom.getText());
            restaurantSelectionne.setEtoiles(Integer.parseInt(txtEtoiles.getText()));
            restaurantSelectionne.setVille(txtVille.getText());
            restaurantSelectionne.setRue(txtRue.getText());
            restaurantSelectionne.setCodePostal(Integer.parseInt(txtCodePostal.getText()));
            
            apiService.updateRestaurant(restaurantSelectionne.getId(), restaurantSelectionne);
            restaurantTable.refresh();
            afficherMessage("Succès", "Restaurant modifié avec succès!");
        } catch (Exception e) {
            afficherErreur("Erreur", "Impossible de modifier le restaurant: " + e.getMessage());
        }
    }

    @FXML
    private void supprimerRestaurant() {
        if (restaurantSelectionne == null) {
            afficherErreur("Erreur", "Veuillez sélectionner un restaurant à supprimer");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le restaurant");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer " + restaurantSelectionne.getNom() + "?");
        
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                apiService.deleteRestaurant(restaurantSelectionne.getId());
                restaurants.remove(restaurantSelectionne);
                viderFormulaire();
                afficherMessage("Succès", "Restaurant supprimé avec succès!");
            } catch (Exception e) {
                afficherErreur("Erreur", "Impossible de supprimer le restaurant: " + e.getMessage());
            }
        }
    }

    @FXML
    private void actualiserRestaurants() {
        chargerRestaurants();
    }

    private void chargerRestaurants() {
        try {
            restaurants.clear();
            restaurants.addAll(apiService.getAllRestaurants());
        } catch (IOException e) {
            afficherErreur("Erreur de connexion", "Impossible de charger les restaurants.\nVérifiez que le backend est démarré sur http://localhost:8080\n\nDétails: " + e.getMessage());
        } catch (Exception e) {
            afficherErreur("Erreur", "Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void remplirFormulaire(Restaurant restaurant) {
        txtNom.setText(restaurant.getNom());
        txtEtoiles.setText(String.valueOf(restaurant.getEtoiles()));
        txtVille.setText(restaurant.getVille());
        txtRue.setText(restaurant.getRue());
        txtCodePostal.setText(String.valueOf(restaurant.getCodePostal()));
    }

    private void viderFormulaire() {
        txtNom.clear();
        txtEtoiles.clear();
        txtVille.clear();
        txtRue.clear();
        txtCodePostal.clear();
        restaurantSelectionne = null;
        restaurantTable.getSelectionModel().clearSelection();
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
