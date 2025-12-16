package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.model.Restaurant;
import be.FeastOrd.FeastOrd.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController {
    
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Récupérer tous les restaurants
     * GET /restaurants
     */
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return ResponseEntity.ok(restaurants);
    }

    /**
     * Récupérer un restaurant par son ID
     * GET /restaurants/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer id) {
        return restaurantRepository.findById(id)
                .map(restaurant -> ResponseEntity.ok(restaurant))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Créer un nouveau restaurant
     * POST /restaurants
     */
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
    }

    /**
     * Modifier un restaurant existant
     * PUT /restaurants/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Integer id, 
                                                       @RequestBody Restaurant restaurant) {
        return restaurantRepository.findById(id)
                .map(existingRestaurant -> {
                    existingRestaurant.setNom(restaurant.getNom());
                    existingRestaurant.setEtoiles(restaurant.getEtoiles());
                    existingRestaurant.setVille(restaurant.getVille());
                    existingRestaurant.setRue(restaurant.getRue());
                    existingRestaurant.setCodePostal(restaurant.getCodePostal());
                    Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
                    return ResponseEntity.ok(updatedRestaurant);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprimer un restaurant
     * DELETE /restaurants/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Integer id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
