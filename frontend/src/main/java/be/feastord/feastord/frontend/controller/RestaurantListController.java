package be.feastord.feastord.frontend.controller;

import be.feastord.feastord.frontend.dto.RestaurantDto;
import be.feastord.feastord.frontend.service.RestaurantServiceFX;
import be.feastord.feastord.frontend.util.SceneManager;
import be.feastord.feastord.frontend.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantListController {

    @FXML
    private ListView<RestaurantDto> restaurantListView;

    @FXML
    private TextField searchField;

    private final RestaurantServiceFX service = new RestaurantServiceFX();

    // Liste complète (source)
    private final ObservableList<RestaurantDto> allRestaurants =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        try {
            List<RestaurantDto> restaurants = service.getAllRestaurants();
            allRestaurants.addAll(restaurants);

            restaurantListView.setItems(allRestaurants);
            configureListView();
            configureSearch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Configuration de l'affichage d'un restaurant
     */
    private void configureListView() {

        restaurantListView.setCellFactory(listView -> new ListCell<>() {

            @Override
            protected void updateItem(RestaurantDto restaurant, boolean empty) {
                super.updateItem(restaurant, empty);

                if (empty || restaurant == null) {
                    setGraphic(null);
                    return;
                }

                Text infos = new Text(
                        restaurant.getNom() + " (" + restaurant.getEtoiles() + "★)\n"
                                + restaurant.getRue() + ", "
                                + restaurant.getCodePostal() + " "
                                + restaurant.getVille()
                );

                Button reserverButton = new Button("Réserver");
                reserverButton.setOnAction(e -> {
                    // Stocker le restaurant sélectionné
                    SessionManager.setSelectedRestaurantId(restaurant.getId());

                    // Aller vers la page réservation
                    SceneManager.switchScene(e, "/views/reservation.fxml");
                });


                HBox box = new HBox(15, infos, reserverButton);
                setGraphic(box);
            }
        });
    }

    /**
     * 🔍 Recherche par nom de restaurant
     */
    private void configureSearch() {

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {

            String search = newValue.toLowerCase().trim();

            if (search.isEmpty()) {
                restaurantListView.setItems(allRestaurants);
                return;
            }

            ObservableList<RestaurantDto> filtered =
                    FXCollections.observableArrayList(
                            allRestaurants.stream()
                                    .filter(r ->
                                            r.getNom() != null &&
                                                    r.getNom().toLowerCase().contains(search)
                                    )
                                    .collect(Collectors.toList())
                    );

            restaurantListView.setItems(filtered);
        });
    }
}
