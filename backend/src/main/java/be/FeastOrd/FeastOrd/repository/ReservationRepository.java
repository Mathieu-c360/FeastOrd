package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository  extends JpaRepository<Reservation,Integer> {

}
