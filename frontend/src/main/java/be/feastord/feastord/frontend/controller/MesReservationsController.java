package be.feastord.feastord.frontend.controller;

import be.feastord.feastord.frontend.dto.MenuDto;
import be.feastord.feastord.frontend.dto.ReservationDto;
import be.feastord.feastord.frontend.service.ReservationApiService;
import be.feastord.feastord.frontend.util.SceneManager;
import be.feastord.feastord.frontend.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class MesReservationsController {

    @FXML
    private ListView<ReservationDto> reservationListView;

    private final ReservationApiService service = new ReservationApiService();

    @FXML
    public void initialize() {

        int clientId = SessionManager.getCurrentUserId();

        try {
            List<ReservationDto> reservations =
                    service.getMesReservations(clientId);

            reservationListView.getItems().setAll(reservations);

            reservationListView.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(ReservationDto r, boolean empty) {
                    super.updateItem(r, empty);

                    if (empty || r == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(creerCarteReservation(r));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger vos réservations.");
        }
    }

    private VBox creerCarteReservation(ReservationDto r) {

        MenuDto menu = r.getMenuClient();



        double prixMenu =
                menu.getEntree().getPrix()
                        + menu.getRepas().getPrix()
                        + menu.getDessert().getPrix();

        double total = prixMenu * r.getNombreClient();

        Label infos = new Label(

                "📅 " + r.getDate() + " à " + r.getHeure() + "\n" +
                        "👥 " + r.getNombreClient() + " personnes\n" +
                        "🍽 Menu : "
                        + menu.getEntree().getNom() + " / "
                        + menu.getRepas().getNom() + " / "
                        + menu.getDessert().getNom() + "\n" +
                        "💰 Total estimé : " + String.format("%.2f", total) + " €\n" +
                        "📌 État : " + r.getEtat()
        );
        Button modifierBtn = new Button("Modifier");
        modifierBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        modifierBtn.setVisible("ATTENTE".equals(r.getEtat()));

        modifierBtn.setOnAction(e -> {
            SessionManager.setReservationToEdit(r);
            SceneManager.switchScene(null, "/views/modifier-reservation.fxml");
        });


        Button annulerBtn = new Button("Annuler");
        annulerBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        // ✅ RÈGLE MÉTIER
        annulerBtn.setVisible("ATTENTE".equals(r.getEtat()));

        annulerBtn.setOnAction(e -> annulerReservation(r));

        VBox box = new VBox(8, infos, modifierBtn, annulerBtn);

        box.setStyle(
                "-fx-border-color: #ccc;"+
                "-fx-border-radius: 6;"+
                "-fx-padding: 12;"+
                "-fx-background-radius: 6;"
                );

        return box;
    }

    private void annulerReservation(ReservationDto r) {
        try {
            service.annulerReservation(r.getId());

            // 🔄 Mise à jour UI
            r.setEtat("ANNULEE");
            reservationListView.refresh();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'annuler la réservation.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
