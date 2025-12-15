package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.dto.ReservationDto;
import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 🔹 Récupérer une réservation
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return reservationService.findReservationById(id);
    }

    // 🔹 Réservations d’un client
    @GetMapping("mes-reservations/{id}")
    public List<Reservation> getReservationByClient(@PathVariable Integer id) {
        return reservationService.findAllReservationsByClient(id);
    }



    // 🔹 Toutes les réservations (admin / debug)
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.findAllReservations();
    }

    // 🔹 Création d’une réservation
    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationDto reservationDto
    ) {
        Reservation reservation = reservationService.saveReservation(
                reservationDto,
                reservationDto.getClientId(),
                reservationDto.getMenuId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    // 🔹 Annuler une réservation (si EN_ATTENTE)
    @PutMapping("/{id}/annuler")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable Integer id) {
        reservationService.cancelReservationById(id);
    }

    // 🔹 Supprimer une réservation
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservationById(id);
    }
    @PutMapping("/{id}")
    public Reservation updateReservation(
            @PathVariable Integer id,
            @RequestBody ReservationDto dto
    ) {
        return reservationService.updateReservationById(id, dto);
    }

}
