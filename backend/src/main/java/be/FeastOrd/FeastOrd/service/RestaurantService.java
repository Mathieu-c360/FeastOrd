package be.FeastOrd.FeastOrd.service;


import be.FeastOrd.FeastOrd.dto.RestaurantDto;
import be.FeastOrd.FeastOrd.model.Restaurant;
import be.FeastOrd.FeastOrd.model.Utilisateur;
import be.FeastOrd.FeastOrd.repository.RestaurantRepository;
import be.FeastOrd.FeastOrd.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UtilisateurRepository utilisateurRepository;


    public RestaurantService(RestaurantRepository restaurantRepository, UtilisateurRepository utilisateurRepository) {
        this.restaurantRepository = restaurantRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Restaurant findRestaurantById(Integer id){
        return restaurantRepository.findById(id).orElseThrow(
                ()
                        -> new IllegalArgumentException("Le restaurant n'existe pas")
        );
    }

    public List<Restaurant> findAllRestaurants(){
        return restaurantRepository.findAll();
    }

    public Restaurant saveRestaurant(RestaurantDto restaurantDto, Integer idUtilisateur){
        Utilisateur user = utilisateurRepository.findById(idUtilisateur).orElseThrow(
                ()-> new IllegalArgumentException("Le utilisateur n'existe pas")
        );
        Restaurant restaurant = new Restaurant();
        restaurant.setNom(restaurantDto.getNom());
        restaurant.setVille(restaurantDto.getVille());
        restaurant.setEtoiles(restaurantDto.getEtoiles());
        restaurant.setRue(restaurantDto.getRue());
        restaurant.setCodePostal(restaurantDto.getCodePostal());
        restaurant.setUtilisateur(user);
        restaurantRepository.save(restaurant);


       return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant( Integer id, RestaurantDto restaurantDto){
        Restaurant oldRestaurant = restaurantRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Le restaurant n'existe pas")
        );
        oldRestaurant.setNom(restaurantDto.getNom());
        oldRestaurant.setVille(restaurantDto.getVille());
        oldRestaurant.setEtoiles(restaurantDto.getEtoiles());
        oldRestaurant.setRue(restaurantDto.getRue());
        oldRestaurant.setCodePostal(restaurantDto.getCodePostal());
       return restaurantRepository.save(oldRestaurant);
    }



}
