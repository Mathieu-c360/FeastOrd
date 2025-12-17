package be.feastord.feastord.frontend.service;

import be.feastord.feastord.frontend.dto.ReservationDto;
import be.feastord.feastord.frontend.dto.ReservationRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ReservationApiService {

    private static final String API_URL =
            "http://localhost:8080/api/reservations";

    private final HttpClient client = HttpClient.newHttpClient();

    private final ObjectMapper mapper;

    public ReservationApiService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public void envoyerReservation(ReservationRequestDto dto) throws Exception {

        String json = mapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201 && response.statusCode() != 200) {
            throw new RuntimeException(
                    "Erreur lors de la réservation (HTTP "
                            + response.statusCode() + ")"
            );
        }
    }

    public List<ReservationDto> getMesReservations(int clientId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/reservations/mes-reservations/" + clientId))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return Arrays.asList(
                mapper.readValue(response.body(), ReservationDto[].class)
        );
    }

    public void annulerReservation(int reservationId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "http://localhost:8080/api/reservations/" + reservationId + "/annuler"
                ))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());
    }

    public void modifierReservation(
            int reservationId,
            ReservationRequestDto dto
    ) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "http://localhost:8080/api/reservations/" + reservationId
                ))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(
                        mapper.writeValueAsString(dto)
                ))
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());
    }


}
