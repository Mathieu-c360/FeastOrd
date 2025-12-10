package be.FeastOrd.javafx; // 1. Changement du package pour correspondre au dossier

import be.FeastOrd.javafx.service.ApiService; // 2. On importe notre pont vers le backend
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    // 3. On crée une instance de notre service API
    private final ApiService apiService = new ApiService();

    @Override
    public void start(Stage stage) {
        int maxlargeur = 200;
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8F8F8;");
        
        VBox BoxFormulaire = new VBox();
        BoxFormulaire.setStyle("-fx-border-color: black; " + "-fx-border-width: 2px; ");
        
        VBox Vboxbouton = new VBox();
        Label formulaire = new Label("Formulaire de contact");
        formulaire.setStyle("-fx-background-color: #3f51b5; " + "-fx-text-fill: white; " + "-fx-font-size: 16pt; " + "-fx-font-weight: bold; " + "-fx-padding: 15px 30px; " + "-fx-border-radius: 5px;");
        
        root.setTop(formulaire);
        BorderPane.setAlignment(formulaire, Pos.CENTER);
        
        Label Lnom = new Label("Entrez votre nom : ");
        Label Lprenom = new Label("Entrez votre prenom : ");
        Label Lmail = new Label("Entrez votre mail: ");
        Label LmotDePasse = new Label("Entrez votre mot de passe");
        Label resultatFormulaire = new Label();
        
        TextField nom = new TextField();
        TextField prenom = new TextField();
        TextField mail = new TextField();
        TextField motDePasse = new TextField(); // Attention: Pour un vrai mot de passe, utilise PasswordField
        
        nom.setMaxWidth(maxlargeur);
        prenom.setMaxWidth(maxlargeur);
        mail.setMaxWidth(maxlargeur);
        motDePasse.setMaxWidth(maxlargeur);
        
        Button validation = new Button("Valider");
        validation.setStyle("-fx-background-color: #4CAF50; " + "-fx-text-fill: white; " + "-fx-font-weight: bold; " + "-fx-padding: 10px 20px; " + "-fx-font-size: 14px;");
        
        // --- C'EST ICI QUE TOUT CHANGE ---
        validation.setOnAction(event -> {
            String InputNom = nom.getText();
            String InputPrenom = prenom.getText();
            String InputMail = mail.getText();
            String InputMotDePasse = motDePasse.getText();

            if (InputNom.isEmpty() || InputPrenom.isEmpty() || InputMail.isEmpty() || InputMotDePasse.isEmpty()) {
                resultatFormulaire.setText("Vous devez remplir tous les champs !");
                resultatFormulaire.setStyle("-fx-text-fill: red;");
            } else {
                // APPEL AU BACKEND VIA L'API
                resultatFormulaire.setText("Envoi en cours...");
                
                // On appelle la méthode qu'on a créée dans ApiService
                boolean succes = apiService.inscrireClient(InputNom, InputPrenom, InputMail, InputMotDePasse);

                if (succes) {
                    resultatFormulaire.setText("Inscription réussie dans la Base de Données !");
                    resultatFormulaire.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    // Tu pourrais vider les champs ici
                    nom.clear(); prenom.clear(); mail.clear(); motDePasse.clear();
                } else {
                    resultatFormulaire.setText("Erreur : Impossible de contacter le serveur ou mail existant.");
                    resultatFormulaire.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });
        // ---------------------------------

        Vboxbouton.setAlignment(Pos.CENTER);
        Vboxbouton.getChildren().add(validation);
        Vboxbouton.setPadding(new Insets(20));
        
        BoxFormulaire.setPadding(new Insets(20));
        BoxFormulaire.setStyle("-fx-background-color: white; -fx-border-color: #BBBBBB; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.5, 0, 0);");
        BoxFormulaire.setAlignment(Pos.CENTER);
        BoxFormulaire.getChildren().addAll(Lnom, nom, Lprenom, prenom, Lmail, mail, LmotDePasse, motDePasse, Vboxbouton, resultatFormulaire);
        
        root.setCenter(BoxFormulaire);
        Scene scene = new Scene(root, 640, 480);

        stage.setTitle("Inscription FeastOrd");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}