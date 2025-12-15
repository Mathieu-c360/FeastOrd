package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.dto.RestaurantDto;
import be.FeastOrd.FeastOrd.model.Restaurant;
import be.FeastOrd.FeastOrd.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer id) {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }


    @PostMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody RestaurantDto restaurantDto,
            @PathVariable Integer idUtilisateur
    ) {
        Restaurant restaurant = restaurantService.saveRestaurant(restaurantDto, idUtilisateur);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Integer id,
            @RequestBody RestaurantDto restaurantDto
    ) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDto);
        return ResponseEntity.ok(updatedRestaurant);
    }
}

