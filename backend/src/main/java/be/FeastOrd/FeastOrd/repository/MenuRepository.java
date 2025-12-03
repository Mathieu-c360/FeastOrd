package be.FeastOrd.FeastOrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.FeastOrd.FeastOrd.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    // Vous pouvez ajouter des méthodes de recherche personnalisées ici si besoin
}
