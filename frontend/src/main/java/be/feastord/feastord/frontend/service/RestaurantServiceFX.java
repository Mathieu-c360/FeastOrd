package be.feastord.feastord.frontend.service;

import be.feastord.feastord.frontend.dto.RestaurantDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RestaurantServiceFX {

    private static final String BASE_URL = "http://localhost:8080/restaurants";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<RestaurantDto> getAllRestaurants() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(
                response.body(),
                new TypeReference<List<RestaurantDto>>() {}
        );
    }
}
