package be.feastord.feastord.frontend.controller;

import be.feastord.feastord.frontend.dto.ReservationDto;
import be.feastord.feastord.frontend.dto.ReservationRequestDto;
import be.feastord.feastord.frontend.service.ReservationApiService;
import be.feastord.feastord.frontend.util.SceneManager;
import be.feastord.feastord.frontend.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ModifierReservationController {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> heureComboBox;
    @FXML private Spinner<Integer> nombreClientSpinner;
    @FXML private TextArea commentaireField;

    private ReservationDto reservation;

    @FXML
    public void initialize() {

        reservation = SessionManager.getReservationToEdit();

        // sécurité
        if (reservation == null) {
            SceneManager.switchScene(null, "/views/mes-reservations.fxml");
            return;
        }

        datePicker.setValue(LocalDate.parse(reservation.getDate()));
        commentaireField.setText(reservation.getCommentaire());

        SpinnerValueFactory<Integer> factory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        5, 50, reservation.getNombreClient()
                );
        nombreClientSpinner.setValueFactory(factory);

        for (int h = 11; h <= 22; h++) {
            heureComboBox.getItems().add(String.format("%02d:00", h));
            heureComboBox.getItems().add(String.format("%02d:30", h));
        }
        heureComboBox.getItems().add("23:00");
        heureComboBox.setValue(reservation.getHeure());
    }

    public void enregistrerModification() {

        try {
            ReservationRequestDto dto = new ReservationRequestDto(
                    SessionManager.getCurrentUserId(),
                    null, // restaurantId pas nécessaire ici
                    reservation.getMenuClient().getId(),
                    datePicker.getValue(),
                    nombreClientSpinner.getValue(),
                    commentaireField.getText(),
                    heureComboBox.getValue()
            );

            new ReservationApiService()
                    .modifierReservation(reservation.getId(), dto);

            SceneManager.switchScene(null, "/views/mes-reservations.fxml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
