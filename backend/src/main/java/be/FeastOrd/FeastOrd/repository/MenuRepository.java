package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    // Récupérer tous les menus d'un restaurant
    List<Menu> findByRestaurantId(Integer restaurantId);
}
