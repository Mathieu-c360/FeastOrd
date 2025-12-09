package be.FeastOrd.FeastOrd.service;

import be.FeastOrd.FeastOrd.dto.ReservationDto;
import be.FeastOrd.FeastOrd.model.*;
import be.FeastOrd.FeastOrd.repository.MenuRepository;
import be.FeastOrd.FeastOrd.repository.ReservationRepository;
import be.FeastOrd.FeastOrd.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private  final ReservationRepository reservationRepository;
    private  final MenuRepository menuRepository;
    private  final UtilisateurRepository utilisateurRepository;

    public ReservationService(ReservationRepository reservationRepository, MenuRepository menuRepository, UtilisateurRepository clientRepository) {
        this.reservationRepository = reservationRepository;
        this.menuRepository = menuRepository;
        this.utilisateurRepository = clientRepository;
    }

    public Reservation saveReservation(ReservationDto reservationRequest, Integer clientId, Integer menuId) {
        if (reservationRequest.getNombreClient() < 5) {
            throw new IllegalArgumentException("Le nombre minimum de personnes est 5.");
        }

        Utilisateur utilisateur = utilisateurRepository.findById(clientId).orElseThrow(()
                -> new IllegalArgumentException("Le client n'existe pas"));
        Menu menu = menuRepository.findById(menuId).orElseThrow(()
                -> new IllegalArgumentException("Le menu n'existe pas"));


        Reservation reservation = Reservation.builder()
                .menuClient(menu)
                .client(utilisateur)
                .nombreClient(reservationRequest.getNombreClient())
                .commentaire(reservationRequest.getCommentaire())
                .date(reservationRequest.getDate())
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

    public Reservation updateReservationById(Integer id, ReservationDto updateReservationRequest, Integer menuId) {
        Reservation  oldReservation = findReservationById(id);
        if (oldReservation != null ) {
            Menu menu = menuRepository.findById(menuId).orElseThrow(()
                    -> new IllegalArgumentException("Le menu n'existe pas"));
            oldReservation.setMenuClient(menu);
            oldReservation.setCommentaire(updateReservationRequest.getCommentaire());
            oldReservation.setDate(updateReservationRequest.getDate());
            oldReservation.setNombreClient(updateReservationRequest.getNombreClient());
            oldReservation.setEtat(updateReservationRequest.getEtat());
        }
        assert oldReservation != null;
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

    public List<Reservation> findAllReservationsByClient(Integer clientId) {
        Utilisateur clientUser = utilisateurRepository.findById(clientId).orElseThrow(()
                -> new IllegalArgumentException("Le client n'existe pas"));

        return  reservationRepository.findByClient(clientUser);
    }
}
