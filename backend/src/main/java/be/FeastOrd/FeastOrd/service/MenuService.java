package be.FeastOrd.FeastOrd.service;

import java.util.List;

import be.FeastOrd.FeastOrd.dto.MenuRequest;
import be.FeastOrd.FeastOrd.exception.BadRequestException;
import be.FeastOrd.FeastOrd.exception.ResourceNotFoundException;
import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.model.Repas;
import be.FeastOrd.FeastOrd.model.Restaurant;
import be.FeastOrd.FeastOrd.repository.MenuRepository;
import be.FeastOrd.FeastOrd.repository.RepasRepository;
import be.FeastOrd.FeastOrd.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final RepasRepository repasRepository;

    public MenuService(MenuRepository menuRepository, 
                       RestaurantRepository restaurantRepository,
                       RepasRepository repasRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.repasRepository = repasRepository;
    }

    // Récupérer tous les menus
    public List<Menu> findAllMenus() {
        return menuRepository.findAll();
    }

    // Récupérer un menu par son ID
    public Menu findMenuById(Integer id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le menu avec l'ID " + id + " n'existe pas."));
    }

    // Récupérer tous les menus d'un restaurant
    public List<Menu> findMenusByRestaurantId(Integer restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId);
    }

    // Créer un menu
    public Menu createMenu(MenuRequest menuRequest) {
        // Validation : restaurantId est obligatoire
        if (menuRequest.getRestaurantId() == null) {
            throw new BadRequestException("L'ID du restaurant est obligatoire.");
        }

        Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Le restaurant avec l'ID " + menuRequest.getRestaurantId() + " n'existe pas."));

        Repas entree = menuRequest.getEntreeId() != null ?
                repasRepository.findById(menuRequest.getEntreeId())
                        .orElseThrow(() -> new ResourceNotFoundException("L'entrée avec l'ID " + menuRequest.getEntreeId() + " n'existe pas.")) : null;

        Repas repas = menuRequest.getRepasId() != null ?
                repasRepository.findById(menuRequest.getRepasId())
                        .orElseThrow(() -> new ResourceNotFoundException("Le repas avec l'ID " + menuRequest.getRepasId() + " n'existe pas.")) : null;

        Repas dessert = menuRequest.getDessertId() != null ?
                repasRepository.findById(menuRequest.getDessertId())
                        .orElseThrow(() -> new ResourceNotFoundException("Le dessert avec l'ID " + menuRequest.getDessertId() + " n'existe pas.")) : null;

        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        menu.setEntree(entree);
        menu.setRepas(repas);
        menu.setDessert(dessert);

        return menuRepository.save(menu);
    }

    // Modifier un menu
    public Menu updateMenu(Integer id, MenuRequest menuRequest) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le menu avec l'ID " + id + " n'existe pas."));

        if (menuRequest.getRestaurantId() != null) {
            Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Le restaurant avec l'ID " + menuRequest.getRestaurantId() + " n'existe pas."));
            menu.setRestaurant(restaurant);
        }

        if (menuRequest.getEntreeId() != null) {
            Repas entree = repasRepository.findById(menuRequest.getEntreeId())
                    .orElseThrow(() -> new ResourceNotFoundException("L'entrée avec l'ID " + menuRequest.getEntreeId() + " n'existe pas."));
            menu.setEntree(entree);
        }

        if (menuRequest.getRepasId() != null) {
            Repas repas = repasRepository.findById(menuRequest.getRepasId())
                    .orElseThrow(() -> new ResourceNotFoundException("Le repas avec l'ID " + menuRequest.getRepasId() + " n'existe pas."));
            menu.setRepas(repas);
        }

        if (menuRequest.getDessertId() != null) {
            Repas dessert = repasRepository.findById(menuRequest.getDessertId())
                    .orElseThrow(() -> new ResourceNotFoundException("Le dessert avec l'ID " + menuRequest.getDessertId() + " n'existe pas."));
            menu.setDessert(dessert);
        }

        return menuRepository.save(menu);
    }

    // Supprimer un menu
    public void deleteMenu(Integer id) {
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Le menu avec l'ID " + id + " n'existe pas.");
        }
        menuRepository.deleteById(id);
    }
}
