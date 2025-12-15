package be.FeastOrd.FeastOrd.service;

import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.model.Restaurant;
import be.FeastOrd.FeastOrd.repository.MenuRepository;
import be.FeastOrd.FeastOrd.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuService(MenuRepository menuRepository,
                       RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Menu> getMenusByRestaurant(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant introuvable"));

        return menuRepository.findByRestaurant(restaurant);
    }
}
