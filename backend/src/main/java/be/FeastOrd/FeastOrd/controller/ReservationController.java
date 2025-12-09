package be.FeastOrd.FeastOrd.controller;


import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.dto.ReservationDto;
import be.FeastOrd.FeastOrd.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feastord")
@CrossOrigin(origins = "*")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return reservationService.findReservationById(id);
    }
    @GetMapping("mes-reservations/{id}")
    public List<Reservation> getReservationByClient(@PathVariable Integer id) {
        return reservationService.findAllReservationsByClient(id);
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.findAllReservations();
    }

    @GetMapping("cancel/{id}")
    public void cancelReservationById(@PathVariable Integer id) {
         reservationService.cancelReservationById(id);
    }

    @PostMapping
    public ResponseEntity<Reservation> saveReservation(@RequestBody ReservationDto reservationRequest, Integer ClientId ,  Integer menuId) {
        return  ResponseEntity.ok(reservationService.saveReservation(reservationRequest, ClientId, menuId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteReservationById(@PathVariable Integer id) {
        reservationService.deleteReservationById(id);
    }


    @PutMapping("/{id}/{id2}")
    public Reservation updateReservation(@PathVariable Integer id,ReservationDto reservationDto, @PathVariable Integer id2) {
        return  reservationService.updateReservationById(id, reservationDto,id2);
    }

}
