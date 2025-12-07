package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.dto.MenuRequest;
import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
@CrossOrigin(origins = "*")
public class MenuController {
    
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * Récupérer tous les menus
     * GET /menus
     */
    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.findAllMenus();
        return ResponseEntity.ok(menus);
    }

    /**
     * Récupérer un menu par son ID
     * GET /menus/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Integer id) {
        Menu menu = menuService.findMenuById(id);
        return ResponseEntity.ok(menu);
    }

    /**
     * Récupérer tous les menus d'un restaurant
     * GET /menus/restaurant/{restaurantId}
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Menu>> getMenusByRestaurant(@PathVariable Integer restaurantId) {
        List<Menu> menus = menuService.findMenusByRestaurantId(restaurantId);
        return ResponseEntity.ok(menus);
    }

    /**
     * Créer un nouveau menu
     * POST /menus
     */
    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody MenuRequest menuRequest) {
        Menu menu = menuService.createMenu(menuRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);
    }

    /**
     * Modifier un menu existant
     * PUT /menus/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Integer id, 
                                          @RequestBody MenuRequest menuRequest) {
        Menu menu = menuService.updateMenu(id, menuRequest);
        return ResponseEntity.ok(menu);
    }

    /**
     * Supprimer un menu
     * DELETE /menus/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Integer id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}
