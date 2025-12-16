package be.FeastOrd.FeastOrd.repository;

import be.FeastOrd.FeastOrd.model.Repas;
import be.FeastOrd.FeastOrd.model.TypeRepas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepasRepository extends JpaRepository<Repas, Integer> {
    List<Repas> findByType(TypeRepas type);
}

