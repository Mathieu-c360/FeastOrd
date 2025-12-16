package be.FeastOrd.Frontend.controller;

import be.FeastOrd.Frontend.model.Menu;
import be.FeastOrd.Frontend.model.MenuRequest;
import be.FeastOrd.Frontend.model.Repas;
import be.FeastOrd.Frontend.model.Restaurant;
import be.FeastOrd.Frontend.service.ApiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class MenuController {
    @FXML
    private TableView<Menu> menuTable;
    
    @FXML
    private TableColumn<Menu, Integer> colMenuId;
    
    @FXML
    private TableColumn<Menu, String> colRestaurant;
    
    @FXML
    private ComboBox<Restaurant> comboRestaurant;
    
    @FXML
    private ComboBox<Restaurant> comboRestaurantForm;
    
    @FXML
    private ComboBox<Repas> comboEntree;
    
    @FXML
    private ComboBox<Repas> comboRepas;
    
    @FXML
    private ComboBox<Repas> comboDessert;
    
    @FXML
    private Button btnAjouter;
    
    @FXML
    private Button btnModifier;
    
    @FXML
    private Button btnSupprimer;
    
    @FXML
    private Button btnActualiser;
    
    @FXML
    private Button btnFiltrer;

    private ApiService apiService;
    private ObservableList<Menu> menus;
    private ObservableList<Restaurant> restaurants;
    private ObservableList<Repas> entrees;
    private ObservableList<Repas> plats;
    private ObservableList<Repas> desserts;
    private Menu menuSelectionne;

    @FXML
    public void initialize() {
        apiService = new ApiService();
        menus = FXCollections.observableArrayList();
        restaurants = FXCollections.observableArrayList();
        entrees = FXCollections.observableArrayList();
        plats = FXCollections.observableArrayList();
        desserts = FXCollections.observableArrayList();
        
        // Configuration des colonnes
        colMenuId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRestaurant.setCellValueFactory(cellData -> {
            Restaurant r = cellData.getValue().getRestaurant();
            return new javafx.beans.property.SimpleStringProperty(r != null ? r.getNom() : "");
        });
        
        menuTable.setItems(menus);
        menuTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                menuSelectionne = newSelection;
                if (newSelection != null) {
                    remplirFormulaire(newSelection);
                }
            }
        );
        
        chargerDonnees();
    }

    @FXML
    private void ajouterMenu() {
        try {
            if (comboRestaurantForm.getValue() == null) {
                afficherErreur("Erreur", "Veuillez sélectionner un restaurant");
                return;
            }
            
            MenuRequest menuRequest = new MenuRequest(
                comboRestaurantForm.getValue().getId(),
                comboEntree.getValue() != null ? comboEntree.getValue().getId() : null,
                comboRepas.getValue() != null ? comboRepas.getValue().getId() : null,
                comboDessert.getValue() != null ? comboDessert.getValue().getId() : null
            );
            
            Menu nouveau = apiService.createMenu(menuRequest);
            menus.add(nouveau);
            viderFormulaire();
            afficherMessage("Succès", "Menu ajouté avec succès!");
        } catch (Exception e) {
            afficherErreur("Erreur", "Impossible d'ajouter le menu: " + e.getMessage());
        }
    }

    @FXML
    private void modifierMenu() {
        if (menuSelectionne == null) {
            afficherErreur("Erreur", "Veuillez sélectionner un menu à modifier");
            return;
        }
        
        try {
            MenuRequest menuRequest = new MenuRequest(
                comboRestaurantForm.getValue() != null ? comboRestaurantForm.getValue().getId() : null,
                comboEntree.getValue() != null ? comboEntree.getValue().getId() : null,
                comboRepas.getValue() != null ? comboRepas.getValue().getId() : null,
                comboDessert.getValue() != null ? comboDessert.getValue().getId() : null
            );
            
            apiService.updateMenu(menuSelectionne.getId(), menuRequest);
            chargerMenus();
            afficherMessage("Succès", "Menu modifié avec succès!");
        } catch (Exception e) {
            afficherErreur("Erreur", "Impossible de modifier le menu: " + e.getMessage());
        }
    }

    @FXML
    private void supprimerMenu() {
        if (menuSelectionne == null) {
            afficherErreur("Erreur", "Veuillez sélectionner un menu à supprimer");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le menu");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce menu?");
        
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                apiService.deleteMenu(menuSelectionne.getId());
                menus.remove(menuSelectionne);
                viderFormulaire();
                afficherMessage("Succès", "Menu supprimé avec succès!");
            } catch (Exception e) {
                afficherErreur("Erreur", "Impossible de supprimer le menu: " + e.getMessage());
            }
        }
    }

    @FXML
    private void filtrerMenus() {
        if (comboRestaurant.getValue() == null) {
            chargerMenus();
            return;
        }
        
        try {
            menus.clear();
            menus.addAll(apiService.getMenusByRestaurant(comboRestaurant.getValue().getId()));
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible de filtrer les menus: " + e.getMessage());
        }
    }

    @FXML
    private void actualiserMenus() {
        chargerDonnees();
    }

    private void chargerDonnees() {
        chargerRestaurants();
        chargerRepas();
        chargerMenus();
    }

    private void chargerRestaurants() {
        try {
            restaurants.clear();
            restaurants.addAll(apiService.getAllRestaurants());
            comboRestaurant.setItems(restaurants);
            comboRestaurantForm.setItems(restaurants);
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible de charger les restaurants: " + e.getMessage());
        }
    }

    private void chargerRepas() {
        try {
            // Charger les entrées
            entrees.clear();
            entrees.addAll(apiService.getRepasByType("ENTREE"));
            comboEntree.setItems(entrees);
            
            // Charger les plats
            plats.clear();
            plats.addAll(apiService.getRepasByType("PLAT"));
            comboRepas.setItems(plats);
            
            // Charger les desserts
            desserts.clear();
            desserts.addAll(apiService.getRepasByType("DESSERT"));
            comboDessert.setItems(desserts);
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible de charger les repas: " + e.getMessage());
        }
    }

    private void chargerMenus() {
        try {
            menus.clear();
            menus.addAll(apiService.getAllMenus());
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible de charger les menus: " + e.getMessage());
        }
    }

    private void remplirFormulaire(Menu menu) {
        comboRestaurantForm.setValue(menu.getRestaurant());
        comboEntree.setValue(menu.getEntree());
        comboRepas.setValue(menu.getRepas());
        comboDessert.setValue(menu.getDessert());
    }

    private void viderFormulaire() {
        comboRestaurantForm.setValue(null);
        comboEntree.setValue(null);
        comboRepas.setValue(null);
        comboDessert.setValue(null);
        menuSelectionne = null;
        menuTable.getSelectionModel().clearSelection();
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
