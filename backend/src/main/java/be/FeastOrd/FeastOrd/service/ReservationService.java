package be.FeastOrd.FeastOrd.service;

import be.FeastOrd.FeastOrd.model.ReservationRequest;
import be.FeastOrd.FeastOrd.model.EtatReservation;
import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.repository.MenuRepository;
import be.FeastOrd.FeastOrd.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation findReservationById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation pas trouvé."));
    }

    public Reservation updateReservationById(Integer id, ReservationRequest updateReservationRequest) {
        Reservation  oldReservation = findReservationById(id);
        if (updateReservationRequest.getCommentaire() != null && !updateReservationRequest.getCommentaire().isEmpty()) {
            Optional<Menu> newMenu = menuRepository.findById(updateReservationRequest.getMenuId());
            oldReservation.setCommentaire(updateReservationRequest.getCommentaire());
            oldReservation.setDate(updateReservationRequest.getDate());
            oldReservation.setNomClient(updateReservationRequest.getNomClient());
            oldReservation.setNombreClient(updateReservationRequest.getNombreClient());
            oldReservation.setEtat(updateReservationRequest.getEtat());
            oldReservation.setMenuClient(newMenu.get());
        }
        return  reservationRepository.save(oldReservation);
    }

    public void deleteReservationById(Integer id) {
        reservationRepository.deleteById(id);
    }

    public void cancelReservationById(Integer id) {
        Reservation  cancelledReservation = findReservationById(id);
        cancelledReservation.setEtat(EtatReservation.ANNULEE);
        reservationRepository.save(cancelledReservation);
    }
}
