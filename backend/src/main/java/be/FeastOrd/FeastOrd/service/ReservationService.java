package be.FeastOrd.FeastOrd.service;

import be.FeastOrd.FeastOrd.exception.ResourceNotFoundException;
import be.FeastOrd.FeastOrd.model.EtatReservation;
import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Récupérer toutes les réservations
     */
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Récupérer une réservation par son ID
     */
    public Reservation findReservationById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La réservation avec l'ID " + id + " n'existe pas."));
    }

    /**
     * Récupérer toutes les réservations d'un restaurant
     */
    public List<Reservation> findReservationsByRestaurantId(Integer restaurantId) {
        return reservationRepository.findByMenuClientRestaurantId(restaurantId);
    }

    /**
     * Récupérer les réservations par état
     */
    public List<Reservation> findReservationsByEtat(EtatReservation etat) {
        return reservationRepository.findByEtat(etat);
    }

    /**
     * Récupérer les réservations d'un restaurant par état
     */
    public List<Reservation> findReservationsByRestaurantIdAndEtat(Integer restaurantId, EtatReservation etat) {
        return reservationRepository.findByMenuClientRestaurantIdAndEtat(restaurantId, etat);
    }

    /**
     * Accepter une réservation
     */
    public Reservation accepterReservation(Integer id) {
        Reservation reservation = findReservationById(id);
        reservation.setEtat(EtatReservation.VALIDEE);
        return reservationRepository.save(reservation);
    }

    /**
     * Refuser une réservation
     */
    public Reservation refuserReservation(Integer id) {
        Reservation reservation = findReservationById(id);
        reservation.setEtat(EtatReservation.REFUSEE);
        return reservationRepository.save(reservation);
    }
}

