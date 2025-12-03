package be.FeastOrd.FeastOrd.service;

import be.FeastOrd.FeastOrd.dto.ReservationRequest;
import be.FeastOrd.FeastOrd.model.EtatReservation;
import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.repository.MenuRepository;
import be.FeastOrd.FeastOrd.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private  final ReservationRepository reservationRepository;
    private  final MenuRepository menuRepository;

    public ReservationService(ReservationRepository reservationRepository, MenuRepository menuRepository) {
        this.reservationRepository = reservationRepository;
        this.menuRepository = menuRepository;
    }

    public Reservation saveReservation(ReservationRequest reservationRequest) {
        if (reservationRequest.getNombreClient() < 5) {
            throw new IllegalArgumentException("Le nombre minimum de personnes est 5.");
        }

        Menu menu = menuRepository.findById(reservationRequest.getMenuId())
                .orElseThrow(() -> new RuntimeException("Le menu n'existe pas."));

        Reservation reservation = Reservation.builder()
                .nomClient(reservationRequest.getNomClient())
                .nombreClient(reservationRequest.getNombreClient())
                .commentaire(reservationRequest.getCommentaire())
                .date(reservationRequest.getDate())
                .menuClient(menu)
                .etat(EtatReservation.ATTENTE)
                .build();

        return   reservationRepository.save(reservation);
    }
}
