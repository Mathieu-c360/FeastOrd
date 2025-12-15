package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Reservation;
import be.FeastOrd.FeastOrd.model.EtatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    
    // Charger les relations avec EntityGraph pour éviter LazyInitializationException
    @EntityGraph(attributePaths = {"menuClient", "menuClient.restaurant", "menuClient.entree", "menuClient.repas", "menuClient.dessert"})
    @Override
    List<Reservation> findAll();
    
    @EntityGraph(attributePaths = {"menuClient", "menuClient.restaurant", "menuClient.entree", "menuClient.repas", "menuClient.dessert"})
    @Override
    Optional<Reservation> findById(Integer id);
    
    // Trouver les réservations par restaurant (via le menu)
    @EntityGraph(attributePaths = {"menuClient", "menuClient.restaurant", "menuClient.entree", "menuClient.repas", "menuClient.dessert"})
    List<Reservation> findByMenuClientRestaurantId(Integer restaurantId);
    
    // Trouver les réservations par état
    @EntityGraph(attributePaths = {"menuClient", "menuClient.restaurant", "menuClient.entree", "menuClient.repas", "menuClient.dessert"})
    List<Reservation> findByEtat(EtatReservation etat);
    
    // Trouver les réservations par restaurant et état
    @EntityGraph(attributePaths = {"menuClient", "menuClient.restaurant", "menuClient.entree", "menuClient.repas", "menuClient.dessert"})
    List<Reservation> findByMenuClientRestaurantIdAndEtat(Integer restaurantId, EtatReservation etat);
}

