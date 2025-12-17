package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository  extends JpaRepository<Reservation,Integer> {


    List<Reservation> findByClient(Utilisateur client);

    List<Reservation> findByClientId(Integer clientId);
}
