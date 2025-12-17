package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findByRestaurant(Restaurant restaurant);
}
