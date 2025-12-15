package be.feastord.feastord.frontend.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    public static void switchScene(ActionEvent event, String fxmlPath) {

        try {
            URL fxmlUrl = SceneManager.class.getResource(fxmlPath);

            if (fxmlUrl == null) {
                throw new RuntimeException("FXML introuvable : " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage;

            if (event != null) {
                stage = (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();
            } else {
                stage = (Stage) Window.getWindows()
                        .stream()
                        .filter(Window::isShowing)
                        .findFirst()
                        .orElseThrow();
            }

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
