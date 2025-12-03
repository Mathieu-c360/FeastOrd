package be.feastord.feastord.frontend.controller;

import be.feastord.feastord.frontend.dto.ReservationRequestFX;
import be.feastord.feastord.frontend.service.ReservationApiService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationControllerFX {

    @FXML private TextField menuIdField;
    @FXML private TextField nomClientField;
    @FXML private TextField nombreClientField;
    @FXML private TextField commentaireField;
    @FXML private TextField dateField;
    @FXML private Label resultLabel;

    private final ReservationApiService api = new ReservationApiService();

    // 🌟 Initialisation automatique de la date par défaut
    @FXML
    public void initialize() {
        dateField.setText("2025-12-17 20:00");
    }

    @FXML
    public void onReservationClick() {

        // 🔹 Parsing du menu & nombre clients
        int menuId;
        int nombre;

        try {
            menuId = Integer.parseInt(menuIdField.getText());
            nombre = Integer.parseInt(nombreClientField.getText());
        } catch (Exception ex) {
            resultLabel.setText("Veuillez entrer un nombre valide.");
            return;
        }

        // 🔹 Parsing de la date, avec message clair
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setLenient(false);
        Date date;

        try {
            date = sdf.parse(dateField.getText());
        } catch (Exception ex) {
            resultLabel.setText("Format date invalide. Exemple : 2025-12-17 20:00");
            return;
        }

        // 🔹 Construction du DTO
        ReservationRequestFX dto = new ReservationRequestFX(
                menuId,
                nomClientField.getText(),
                nombre,
                commentaireField.getText(),
                date
        );

        // 🔹 Appel API
        try {
            String response = api.envoyerReservation(dto);
            resultLabel.setText("Réservation envoyée : " + response);
        } catch (Exception e) {
            resultLabel.setText("Erreur API : " + e.getMessage());
        }
    }
}
