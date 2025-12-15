package be.feastord.feastord.frontend.controller;

import be.feastord.feastord.frontend.dto.MenuDto;
import be.feastord.feastord.frontend.dto.ReservationDto;
import be.feastord.feastord.frontend.dto.ReservationRequestDto;
import be.feastord.feastord.frontend.service.MenuApiService;
import be.feastord.feastord.frontend.service.ReservationApiService;
import be.feastord.feastord.frontend.util.SceneManager;
import be.feastord.feastord.frontend.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReservationController {

    @FXML
    private Label restaurantLabel;

    @FXML
    private ComboBox<MenuDto> menuComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner<Integer> nombreClientSpinner;

    @FXML
    private TextArea commentaireField;

    @FXML
    private ComboBox<String> heureComboBox;

    // Labels pour afficher le contenu du menu
    @FXML
    private Label entreeLabel;

    @FXML
    private Label platLabel;

    @FXML
    private Label dessertLabel;

    @FXML
    public void initialize() {

        Integer restaurantId = SessionManager.getSelectedRestaurantId();
        restaurantLabel.setText("Restaurant sélectionné (id = " + restaurantId + ")");



        // 🔧 Spinner : minimum 5 personnes
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 50, 5);
        nombreClientSpinner.setValueFactory(valueFactory);

        // 📅 Date par défaut
        datePicker.setValue(LocalDate.now());

        // ⏰ Heures entre 11h et 23h (30 min)
        for (int h = 11; h <= 22; h++) {
            heureComboBox.getItems().add(String.format("%02d:00", h));
            heureComboBox.getItems().add(String.format("%02d:30", h));
        }
        heureComboBox.getItems().add("23:00");

        // 🍽 Chargement des vrais menus depuis le backend
        chargerMenus(restaurantId);
    }

    private void chargerMenus(Integer restaurantId) {
        try {
            MenuApiService menuService = new MenuApiService();
            List<MenuDto> menus = menuService.getMenusByRestaurant(restaurantId);

            menuComboBox.getItems().addAll(menus);

            // Affichage lisible dans la ComboBox
            menuComboBox.setCellFactory(cb -> new ListCell<>() {
                @Override
                protected void updateItem(MenuDto menu, boolean empty) {
                    super.updateItem(menu, empty);
                    if (menu == null || empty) {
                        setText(null);
                    } else {
                        setText("Menu #" + menu.getId());
                    }
                }
            });
            menuComboBox.setButtonCell(menuComboBox.getCellFactory().call(null));

            // Affichage dynamique des détails du menu
            menuComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    afficherMenu(newVal);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les menus du restaurant.");
        }
    }

    private void afficherMenu(MenuDto menu) {

        entreeLabel.setText(
                "🍽 ENTREE : " + menu.getEntree().getNom()
                        + " - " + menu.getEntree().getPrix() + " €"
        );

        platLabel.setText(
                "🍝 PLAT : " + menu.getRepas().getNom()
                        + " - " + menu.getRepas().getPrix() + " €"
        );

        dessertLabel.setText(
                "🍰 DESSERT : " + menu.getDessert().getNom()
                        + " - " + menu.getDessert().getPrix() + " €"
        );
    }

    public void confirmerReservation() {

        // 🔒 Vérifications frontend
        if (menuComboBox.getValue() == null) {
            showAlert("Erreur", "Veuillez sélectionner un menu.");
            return;
        }

        if (datePicker.getValue() == null ||
                datePicker.getValue().isBefore(LocalDate.now())) {
            showAlert("Erreur", "Veuillez choisir une date valide.");
            return;
        }

        if (heureComboBox.getValue() == null) {
            showAlert("Erreur", "Veuillez choisir une heure.");
            return;
        }

        try {
            Integer clientId = SessionManager.getCurrentUserId();
            Integer restaurantId = SessionManager.getSelectedRestaurantId();

            MenuDto selectedMenu = menuComboBox.getValue();

            // 🔹 Création du DTO avec le vrai menuId
            ReservationRequestDto dto = new ReservationRequestDto(
                    clientId,
                    restaurantId,
                    selectedMenu.getId(),
                    datePicker.getValue(),
                    nombreClientSpinner.getValue(),
                    commentaireField.getText(),
                    heureComboBox.getValue()
            );

            ReservationApiService service = new ReservationApiService();
            service.envoyerReservation(dto);

            showAlert(
                    "Succès",
                    "Votre réservation a été enregistrée.\nStatut : ATTENTE"
            );

            SceneManager.switchScene(null, "/views/restaurant-list.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de créer la réservation.");
        }
    }




    public void retour(javafx.event.ActionEvent event) {
        SceneManager.switchScene(event, "/views/restaurant-list.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
