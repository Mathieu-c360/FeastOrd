package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;


public interface MenuRepository extends JpaRepository<Menu, Integer> {
    // Récupérer tous les menus d'un restaurant
    //List<Menu> findByRestaurantId(Integer restaurantId);

    // Charger les relations avec EntityGraph pour éviter LazyInitializationException
    @EntityGraph(attributePaths = {"restaurant", "entree", "repas", "dessert"})
    @Override
    List<Menu> findAll();

    @EntityGraph(attributePaths = {"restaurant", "entree", "repas", "dessert"})
    @Override
    Optional<Menu> findById(Integer id);

    @EntityGraph(attributePaths = {"restaurant", "entree", "repas", "dessert"})
    List<Menu> findByRestaurantId(Integer restaurantId);

}
