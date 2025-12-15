package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.model.EtatReservation;
import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Récupérer toutes les réservations
     * GET /reservations
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    /**
     * Récupérer une réservation par son ID
     * GET /reservations/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id) {
        Reservation reservation = reservationService.findReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    /**
     * Récupérer toutes les réservations d'un restaurant
     * GET /reservations/restaurant/{restaurantId}
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Reservation>> getReservationsByRestaurant(@PathVariable Integer restaurantId) {
        List<Reservation> reservations = reservationService.findReservationsByRestaurantId(restaurantId);
        return ResponseEntity.ok(reservations);
    }

    /**
     * Récupérer les réservations par état
     * GET /reservations/etat/{etat}
     * Exemples: /reservations/etat/EN_ATTENTE, /reservations/etat/ACCEPTEE, /reservations/etat/REFUSEE
     */
    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<Reservation>> getReservationsByEtat(@PathVariable EtatReservation etat) {
        List<Reservation> reservations = reservationService.findReservationsByEtat(etat);
        return ResponseEntity.ok(reservations);
    }

    /**
     * Récupérer les réservations d'un restaurant par état
     * GET /reservations/restaurant/{restaurantId}/etat/{etat}
     */
    @GetMapping("/restaurant/{restaurantId}/etat/{etat}")
    public ResponseEntity<List<Reservation>> getReservationsByRestaurantAndEtat(
            @PathVariable Integer restaurantId,
            @PathVariable EtatReservation etat) {
        List<Reservation> reservations = reservationService.findReservationsByRestaurantIdAndEtat(restaurantId, etat);
        return ResponseEntity.ok(reservations);
    }

    /**
     * Accepter une réservation
     * PUT /reservations/{id}/accepter
     */
    @PutMapping("/{id}/accepter")
    public ResponseEntity<Reservation> accepterReservation(@PathVariable Integer id) {
        Reservation reservation = reservationService.accepterReservation(id);
        return ResponseEntity.ok(reservation);
    }

    /**
     * Refuser une réservation
     * PUT /reservations/{id}/refuser
     */
    @PutMapping("/{id}/refuser")
    public ResponseEntity<Reservation> refuserReservation(@PathVariable Integer id) {
        Reservation reservation = reservationService.refuserReservation(id);
        return ResponseEntity.ok(reservation);
    }
}
