package be.FeastOrd.FeastOrd.controller;


import be.FeastOrd.FeastOrd.dto.ReservationRequest;
import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> saveReservation(@RequestBody ReservationRequest reservationRequest) {
        return  ResponseEntity.ok(reservationService.saveReservation(reservationRequest));
    }
}
