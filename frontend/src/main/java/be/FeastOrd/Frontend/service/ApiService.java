package be.FeastOrd.Frontend.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import be.FeastOrd.Frontend.model.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApiService {
    private static final String BASE_URL = "http://localhost:8080";
    private final Gson gson;

    public ApiService() {
        this.gson = new Gson();
    }

    // ========== RESTAURANTS ==========
    public List<Restaurant> getAllRestaurants() throws IOException {
        return getList(BASE_URL + "/restaurants", new TypeToken<List<Restaurant>>(){}.getType());
    }

    public Restaurant getRestaurantById(int id) throws IOException {
        return getObject(BASE_URL + "/restaurants/" + id, Restaurant.class);
    }

    public Restaurant createRestaurant(Restaurant restaurant) throws IOException {
        return postObject(BASE_URL + "/restaurants", restaurant, Restaurant.class);
    }

    public Restaurant updateRestaurant(int id, Restaurant restaurant) throws IOException {
        return putObject(BASE_URL + "/restaurants/" + id, restaurant, Restaurant.class);
    }

    public void deleteRestaurant(int id) throws IOException {
        deleteObject(BASE_URL + "/restaurants/" + id);
    }

    // ========== REPAS ==========
    public List<Repas> getAllRepas() throws IOException {
        return getList(BASE_URL + "/repas", new TypeToken<List<Repas>>(){}.getType());
    }

    public List<Repas> getRepasByType(String type) throws IOException {
        return getList(BASE_URL + "/repas/type/" + type, new TypeToken<List<Repas>>(){}.getType());
    }

    public Repas getRepasById(int id) throws IOException {
        return getObject(BASE_URL + "/repas/" + id, Repas.class);
    }

    public Repas createRepas(Repas repas) throws IOException {
        return postObject(BASE_URL + "/repas", repas, Repas.class);
    }

    public Repas updateRepas(int id, Repas repas) throws IOException {
        return putObject(BASE_URL + "/repas/" + id, repas, Repas.class);
    }

    public void deleteRepas(int id) throws IOException {
        deleteObject(BASE_URL + "/repas/" + id);
    }

    // ========== MENUS ==========
    public List<Menu> getAllMenus() throws IOException {
        return getList(BASE_URL + "/menus", new TypeToken<List<Menu>>(){}.getType());
    }

    public List<Menu> getMenusByRestaurant(int restaurantId) throws IOException {
        return getList(BASE_URL + "/menus/restaurant/" + restaurantId, 
                      new TypeToken<List<Menu>>(){}.getType());
    }

    public Menu getMenuById(int id) throws IOException {
        return getObject(BASE_URL + "/menus/" + id, Menu.class);
    }

    public Menu createMenu(MenuRequest menuRequest) throws IOException {
        return postObject(BASE_URL + "/menus", menuRequest, Menu.class);
    }

    public Menu updateMenu(int id, MenuRequest menuRequest) throws IOException {
        return putObject(BASE_URL + "/menus/" + id, menuRequest, Menu.class);
    }

    public void deleteMenu(int id) throws IOException {
        deleteObject(BASE_URL + "/menus/" + id);
    }

    // ========== RESERVATIONS ==========
    public List<Reservation> getAllReservations() throws IOException {
        return getList(BASE_URL + "/reservations", new TypeToken<List<Reservation>>(){}.getType());
    }

    public List<Reservation> getReservationsByRestaurant(int restaurantId) throws IOException {
        return getList(BASE_URL + "/reservations/restaurant/" + restaurantId, 
                      new TypeToken<List<Reservation>>(){}.getType());
    }

    public Reservation getReservationById(int id) throws IOException {
        return getObject(BASE_URL + "/reservations/" + id, Reservation.class);
    }

    public Reservation accepterReservation(int id) throws IOException {
        return putObject(BASE_URL + "/reservations/" + id + "/accepter", null, Reservation.class);
    }

    public Reservation refuserReservation(int id) throws IOException {
        return putObject(BASE_URL + "/reservations/" + id + "/refuser", null, Reservation.class);
    }

    // ========== MÉTHODES GÉNÉRIQUES ==========
    private <T> T getObject(String urlString, Class<T> clazz) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                return gson.fromJson(response, clazz);
            }
        } else {
            throw new IOException("Erreur HTTP: " + conn.getResponseCode());
        }
    }

    private <T> List<T> getList(String urlString, Type type) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                if (response.isEmpty()) {
                    return new ArrayList<>();
                }
                return gson.fromJson(response, type);
            }
        } else {
            throw new IOException("Erreur HTTP: " + conn.getResponseCode());
        }
    }

    private <T> T postObject(String urlString, Object data, Class<T> responseClass) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        if (data != null) {
            String json = gson.toJson(data);
            System.out.println("Envoi POST à " + urlString + " avec données: " + json);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Code de réponse HTTP: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK || 
            responseCode == HttpURLConnection.HTTP_CREATED) {
            try (Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                System.out.println("Réponse du serveur: " + response);
                if (response.isEmpty()) {
                    throw new IOException("Réponse vide du serveur");
                }
                return gson.fromJson(response, responseClass);
            }
        } else {
            // Lire le message d'erreur du serveur si disponible
            String errorMessage = "Erreur HTTP: " + responseCode;
            try (Scanner scanner = new Scanner(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                if (scanner.hasNext()) {
                    String errorResponse = scanner.useDelimiter("\\A").next();
                    errorMessage += "\n" + errorResponse;
                }
            } catch (Exception e) {
                // Ignorer si on ne peut pas lire l'erreur
            }
            throw new IOException(errorMessage);
        }
    }

    private <T> T putObject(String urlString, Object data, Class<T> responseClass) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        if (data != null) {
            String json = gson.toJson(data);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                return gson.fromJson(response, responseClass);
            }
        } else {
            throw new IOException("Erreur HTTP: " + conn.getResponseCode());
        }
    }

    private void deleteObject(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && 
            conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
            throw new IOException("Erreur HTTP: " + conn.getResponseCode());
        }
    }
}
