package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation,Integer> {
}
