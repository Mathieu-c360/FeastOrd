package be.FeastOrd.FeastOrd.service;

import java.util.List;
import java.util.Optional;

import be.FeastOrd.FeastOrd.model.Menu;
import be.FeastOrd.FeastOrd.repository.MenuRepository;

public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // Récupérer tous les menus
    public List<Menu> findAllMenus() {
        return menuRepository.findAll();
    }

    // Récupérer un menu par son ID
    public Optional<Menu> findMenuById(Integer id) {
        return menuRepository.findById(id);
    }

    // Enregistrer ou mettre à jour un menu
    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    // Supprimer un menu
    public void deleteMenu(Integer id) {
        menuRepository.deleteById(id);
    }
}
