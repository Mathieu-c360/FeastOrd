package be.FeastOrd.javafx.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ApiService {
    // On pointe vers /api/utilisateurs 
    private static final String BACKEND_URL = "http://localhost:8080/api/utilisateurs";
    
    // Outils pour envoyer les requêtes et gérer le JSON
    private final HttpClient client = HttpClient.newHttpClient(); //lien entre javaFX et springboot
    private final ObjectMapper mapper = new ObjectMapper(); //traduction java en json et inversément

    // Méthode appelée par le bouton vlider
    public boolean inscrireClient(String nom, String prenom, String mail, String mdp) {
        try {

            Map<String, String> data = new HashMap<>(); //Création d'un dictionnaire
            data.put("nom", nom);
            data.put("prenom", prenom);
            data.put("mail", mail);
            data.put("motDePasse", mdp);
            data.put("role", "CLIENT");

            String json = mapper.writeValueAsString(data); //traduction en json

            HttpRequest request = HttpRequest.newBuilder() //enveloppe http
                    .uri(URI.create(BACKEND_URL + "/register/client")) 
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();


            System.out.println("Envoi en cours vers : " + request.uri());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //reçoit la réponse du serveur


            if (response.statusCode() == 200) {
                System.out.println("Succès ! Backend a répondu : " + response.body());
                return true;
            } else {
                System.out.println("Erreur Backend (" + response.statusCode() + ") : " + response.body());
                return false;
            }

        } catch (Exception e) { //si on a pas du tout réussi à parler avec le serveur

            System.err.println("Impossible de contacter le serveur : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean inscrireGestionnaire(String nom, String prenom, String mail, String mdp) {
        try {

            Map<String, String> data = new HashMap<>(); //Création d'un dictionnaire
            data.put("nom", nom);
            data.put("prenom", prenom);
            data.put("mail", mail);
            data.put("motDePasse", mdp);
            data.put("role", "Gestionnaire");

            String json = mapper.writeValueAsString(data); //traduction en json

            HttpRequest request = HttpRequest.newBuilder() //enveloppe http
                    .uri(URI.create(BACKEND_URL + "/register/gestionnaire")) 
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();


            System.out.println("Envoi en cours vers : " + request.uri());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //reçoit la réponse du serveur


            if (response.statusCode() == 200) {
                System.out.println("Succès ! Backend a répondu : " + response.body());
                return true;
            } else {
                System.out.println("Erreur Backend (" + response.statusCode() + ") : " + response.body());
                return false;
            }

        } catch (Exception e) { //si on a pas du tout réussi à parler avec le serveur

            System.err.println("Impossible de contacter le serveur : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean seConnecter(String mail, String mdp) 
    {
        try 
        {
            //On prépare les paramètres pour l'URL 
            String mailEncode = URLEncoder.encode(mail, StandardCharsets.UTF_8);
            String mdpEncode = URLEncoder.encode(mdp, StandardCharsets.UTF_8);

            //On construit l'URL complète avec les paramètres
            String urlComplete = BACKEND_URL + "/login?mail=" + mailEncode + "&motDePasse=" + mdpEncode;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlComplete))
                    .header("Content-Type", "application/json") 
                    .GET() 
                    .build();

            // 4. Envoi
            System.out.println("Envoi GET vers : " + urlComplete);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Connexion réussie : " + response.body());
                return true;
            } else {
                System.out.println("Echec connexion (" + response.statusCode() + ")");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
