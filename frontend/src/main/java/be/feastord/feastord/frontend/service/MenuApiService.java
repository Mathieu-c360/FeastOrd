package be.feastord.feastord.frontend.service;

import be.feastord.feastord.frontend.dto.MenuDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class MenuApiService {

    private static final String API_URL =
            "http://localhost:8080/api/menus/restaurant/";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<MenuDto> getMenusByRestaurant(int restaurantId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + restaurantId))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return Arrays.asList(
                mapper.readValue(response.body(), MenuDto[].class)
        );
    }
}
