package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}

