package be.FeastOrd.FeastOrd.service;

import be.FeastOrd.FeastOrd.dto.ReservationDto;
import be.FeastOrd.FeastOrd.model.EtatReservation;
import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.model.Utilisateur;
import be.FeastOrd.FeastOrd.repository.MenuRepository;
import be.FeastOrd.FeastOrd.repository.ReservationRepository;
import be.FeastOrd.FeastOrd.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MenuRepository menuRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              MenuRepository menuRepository,
                              UtilisateurRepository utilisateurRepository) {
        this.reservationRepository = reservationRepository;
        this.menuRepository = menuRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // 🔹 Création d’une réservation
    public Reservation saveReservation(ReservationDto reservationRequest,
                                       Integer clientId,
                                       Integer menuId) {

        if (reservationRequest.getNombreClient() < 5) {
            throw new IllegalArgumentException(
                    "Le nombre minimum de personnes est 5."
            );
        }

        Utilisateur utilisateur = utilisateurRepository.findById(clientId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Le client n'existe pas"));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Le menu n'existe pas"));

        Reservation reservation = Reservation.builder()
                .menuClient(menu)
                .client(utilisateur)
                .nombreClient(reservationRequest.getNombreClient())
                .commentaire(reservationRequest.getCommentaire())
                .date(reservationRequest.getDate())
                .heure(reservationRequest.getHeure())
                // 🔒 état imposé côté backend
                .etat(EtatReservation.ATTENTE)
                .build();

        return reservationRepository.save(reservation);
    }

    // 🔹 Toutes les réservations
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    // 🔹 Une réservation par id
    public Reservation findReservationById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Réservation non trouvée"));
    }

    // 🔹 Réservations d’un client
    public List<Reservation> findAllReservationsByClient(Integer clientId) {
        return reservationRepository.findByClientId(clientId);
    }

    // 🔹 Mise à jour (sans toucher à l’état)
    public Reservation updateReservationById(Integer id, ReservationDto dto) {

        Reservation reservation = findReservationById(id);

        if (reservation.getEtat() != EtatReservation.ATTENTE) {
            throw new IllegalStateException(
                    "Modification impossible : réservation non en attente"
            );
        }

        reservation.setDate(dto.getDate());
        reservation.setNombreClient(dto.getNombreClient());
        reservation.setCommentaire(dto.getCommentaire());

        return reservationRepository.save(reservation);
    }


    // 🔹 Annuler une réservation (SEULEMENT si EN_ATTENTE)
    public void cancelReservationById(Integer id) {

        Reservation reservation = findReservationById(id);

        if (reservation.getEtat() != EtatReservation.ATTENTE) {
            throw new IllegalStateException(
                    "La réservation ne peut plus être annulée"
            );
        }

        reservation.setEtat(EtatReservation.ANNULEE);
        reservationRepository.save(reservation);
    }

    // 🔹 Refuser une réservation (SEULEMENT si EN_ATTENTE)
    public void refuseReservationById(Integer id) {

        Reservation reservation = findReservationById(id);

        if (reservation.getEtat() != EtatReservation.ATTENTE) {
            throw new IllegalStateException(
                    "La réservation ne peut plus être refusée"
            );
        }

        reservation.setEtat(EtatReservation.REFUSEE);
        reservationRepository.save(reservation);
    }


    // 🔹 Suppression définitive
    public void deleteReservationById(Integer id) {
        reservationRepository.deleteById(id);
    }
}
