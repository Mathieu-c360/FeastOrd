package be.FeastOrd.javafx;

import be.FeastOrd.javafx.service.ApiService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private final ApiService apiService = new ApiService();
    private Stage primaryStage; 

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Application FeastOrd");
        afficherMenu();
        stage.show();
    }
    //menu
    private void afficherMenu() {
        VBox layoutMenu = new VBox(20); 
        layoutMenu.setAlignment(Pos.CENTER);
        layoutMenu.setStyle("-fx-background-color: #F8F8F8;");

        Label titre = new Label("Bienvenue sur FeastOrd");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #3f51b5;");

        Button btnInscription = new Button("S'inscrire");
        styleButton(btnInscription, "#2196F3"); 
        btnInscription.setPrefWidth(200);
        
        Button btnConnexion = new Button("Se connecter");
        styleButton(btnConnexion, "#4CAF50"); 
        btnConnexion.setPrefWidth(200);

        btnInscription.setOnAction(e -> afficherFormulaireInscription());
        btnConnexion.setOnAction(e -> afficherFormulaireConnexion());

        layoutMenu.getChildren().addAll(titre, btnConnexion, btnInscription);
        
        //Taille standard 640x480
        primaryStage.setScene(new Scene(layoutMenu, 640, 480));
    }

    //connexion
    private void afficherFormulaireConnexion() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8F8F8;");

        Label titre = new Label("Connexion");
        titre.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16pt; -fx-font-weight: bold; -fx-padding: 15px 30px; -fx-background-radius: 5px;");
        BorderPane.setAlignment(titre, Pos.CENTER);
        BorderPane.setMargin(titre, new Insets(20));
        root.setTop(titre);

        TextField champMail = new TextField(); champMail.setPromptText("Votre adresse mail"); champMail.setMaxWidth(300);
        PasswordField champMdp = new PasswordField(); champMdp.setPromptText("Votre mot de passe"); champMdp.setMaxWidth(300);
        Label labelResultat = new Label();

        Button btnValider = new Button("Se connecter"); styleButton(btnValider, "#4CAF50");
        Button btnRetour = new Button("Retour au menu"); styleButton(btnRetour, "#757575"); // Gris
        

        btnValider.setOnAction(e -> {
            String mail = champMail.getText();
            String mdp = champMdp.getText();

            if (mail.isEmpty() || mdp.isEmpty()) {
                labelResultat.setText("Veuillez remplir tous les champs.");
                labelResultat.setStyle("-fx-text-fill: red;");
            } else {
                labelResultat.setText("Connexion en cours...");
                boolean success = apiService.seConnecter(mail, mdp);
                if (success) {
                    labelResultat.setText("Connexion réussie !");
                    labelResultat.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                } else {
                    labelResultat.setText("Email ou mot de passe incorrect.");
                    labelResultat.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });
        btnRetour.setOnAction(e -> afficherMenu());

        VBox boxForm = new VBox(15);
        boxForm.setAlignment(Pos.CENTER);
        boxForm.setPadding(new Insets(20));
        boxForm.setStyle("-fx-background-color: white; -fx-border-color: #DDD; -fx-border-width: 1px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.5, 0, 0);");
        boxForm.setMaxWidth(400);
        
        boxForm.getChildren().addAll(
            new Label("Email :"), champMail, 
            new Label("Mot de passe :"), champMdp, 
            btnValider, btnRetour, labelResultat
        );

        root.setCenter(boxForm);
        primaryStage.setScene(new Scene(root, 640, 480));
    }

    //inscription
    private void afficherFormulaireInscription() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8F8F8;");

        Label titre = new Label("Créer un compte");
        titre.setStyle("-fx-background-color: #3f51b5; -fx-text-fill: white; -fx-font-size: 16pt; -fx-font-weight: bold; -fx-padding: 15px 30px; -fx-background-radius: 5px;");
        BorderPane.setAlignment(titre, Pos.CENTER);
        BorderPane.setMargin(titre, new Insets(20));
        root.setTop(titre);

        TextField nom = new TextField(); nom.setPromptText("Nom"); nom.setMaxWidth(250);
        TextField prenom = new TextField(); prenom.setPromptText("Prénom"); prenom.setMaxWidth(250);
        TextField mail = new TextField(); mail.setPromptText("Email"); mail.setMaxWidth(250);
        PasswordField motDePasse = new PasswordField(); motDePasse.setPromptText("Mot de passe"); motDePasse.setMaxWidth(250);
        
        Label labelRole = new Label("Type de compte :");
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Client", "Gestionnaire");
        roleBox.setValue("Client");
        roleBox.setMaxWidth(250);

        Label resultatFormulaire = new Label();
        
        Button btnValider = new Button("S'inscrire"); styleButton(btnValider, "#2196F3"); 
        Button btnRetour = new Button("Retour au menu"); styleButton(btnRetour, "#757575");

        btnValider.setOnAction(event -> {
            String iNom = nom.getText();
            String iPrenom = prenom.getText();
            String iMail = mail.getText();
            String iMdp = motDePasse.getText();
            String roleChoisi = roleBox.getValue();

            if (iNom.isEmpty() || iPrenom.isEmpty() || iMail.isEmpty() || iMdp.isEmpty()) {
                resultatFormulaire.setText("Attention : Remplissez tous les champs !");
                resultatFormulaire.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                resultatFormulaire.setText("Envoi en cours...");
                resultatFormulaire.setStyle("-fx-text-fill: black;");
                
                boolean succes;
                if (roleChoisi.equals("Gestionnaire")) {
                    succes = apiService.inscrireGestionnaire(iNom, iPrenom, iMail, iMdp);
                } else {
                    succes = apiService.inscrireClient(iNom, iPrenom, iMail, iMdp);
                }

                if (succes) {
                    resultatFormulaire.setText("Compte " + roleChoisi + " créé !");
                    resultatFormulaire.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    nom.clear(); prenom.clear(); mail.clear(); motDePasse.clear();
                } else {
                    resultatFormulaire.setText("format email incorrecte ou email existant.");
                    resultatFormulaire.setStyle("-fx-text-fill: red;");
                }
            }
        });
        btnRetour.setOnAction(e -> afficherMenu());

        //mise en paeg
        VBox boxForm = new VBox(10); 
        boxForm.setAlignment(Pos.CENTER);
        boxForm.setPadding(new Insets(20));
        boxForm.setStyle("-fx-background-color: white; -fx-border-color: #DDD; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.5, 0, 0);");
        boxForm.setMaxWidth(400);

        boxForm.getChildren().addAll(
            new Label("Nom"), nom, 
            new Label("Prénom"), prenom, 
            new Label("Email"), mail, 
            new Label("Mot de passe"), motDePasse,labelRole, roleBox,btnValider,btnRetour,resultatFormulaire
        );

        root.setCenter(boxForm);
    
        primaryStage.setScene(new Scene(root, 640, 650));
    
        primaryStage.centerOnScreen();
    }

    private void styleButton(Button btn, String colorHex) {
        btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-cursor: hand;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}