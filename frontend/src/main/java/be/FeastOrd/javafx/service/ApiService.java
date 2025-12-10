package be.FeastOrd.javafx.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ApiService {

    // 1. L'adresse exacte de ton Backend (Vérifie le port 8080)
    // On pointe vers /api/utilisateurs car c'est ce qu'on a mis dans le Controller Backend
    private static final String BACKEND_URL = "http://localhost:8080/api/utilisateurs";
    
    // Outils pour envoyer les requêtes et gérer le JSON
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // Méthode appelée par ton bouton "Valider"
    public boolean inscrireClient(String nom, String prenom, String mail, String mdp) {
        try {
            // A. On range les données dans une Map (comme un dictionnaire)
            Map<String, String> data = new HashMap<>();
            data.put("nom", nom);
            data.put("prenom", prenom);
            data.put("mail", mail);
            data.put("motDePasse", mdp);

            // B. On transforme ça en texte JSON : {"nom":"...", "prenom":"..."}
            String json = mapper.writeValueAsString(data);

            // C. On construit la requête POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL + "/register/client")) // L'URL précise pour créer un CLIENT
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // D. On envoie et on attend la réponse
            System.out.println("Envoi en cours vers : " + request.uri());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // E. On analyse le résultat
            if (response.statusCode() == 200) {
                System.out.println("Succès ! Backend a répondu : " + response.body());
                return true;
            } else {
                System.out.println("Erreur Backend (" + response.statusCode() + ") : " + response.body());
                return false;
            }

        } catch (Exception e) {
            // Si le serveur est éteint ou s'il y a un bug
            System.err.println("Impossible de contacter le serveur : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}