package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Menu> getMenusByRestaurant(@PathVariable Integer restaurantId) {
        return menuService.getMenusByRestaurant(restaurantId);
    }
}
