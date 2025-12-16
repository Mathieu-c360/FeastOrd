package be.FeastOrd.FeastOrd.controller;

import be.FeastOrd.FeastOrd.model.Repas;
import be.FeastOrd.FeastOrd.model.TypeRepas;
import be.FeastOrd.FeastOrd.repository.RepasRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repas")
@CrossOrigin(origins = "*")
public class RepasController {
    
    private final RepasRepository repasRepository;

    public RepasController(RepasRepository repasRepository) {
        this.repasRepository = repasRepository;
    }

    /**
     * Récupérer tous les repas
     * GET /repas
     */
    @GetMapping
    public ResponseEntity<List<Repas>> getAllRepas() {
        List<Repas> repas = repasRepository.findAll();
        return ResponseEntity.ok(repas);
    }

    /**
     * Récupérer un repas par son ID
     * GET /repas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Repas> getRepasById(@PathVariable Integer id) {
        return repasRepository.findById(id)
                .map(repas -> ResponseEntity.ok(repas))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupérer les repas par type
     * GET /repas/type/{type}
     * Exemples: /repas/type/ENTREE, /repas/type/PLAT, /repas/type/DESSERT
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Repas>> getRepasByType(@PathVariable TypeRepas type) {
        List<Repas> repas = repasRepository.findByType(type);
        return ResponseEntity.ok(repas);
    }

    /**
     * Créer un nouveau repas
     * POST /repas
     */
    @PostMapping
    public ResponseEntity<Repas> createRepas(@RequestBody Repas repas) {
        Repas savedRepas = repasRepository.save(repas);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(savedRepas);
    }

    /**
     * Modifier un repas existant
     * PUT /repas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Repas> updateRepas(@PathVariable Integer id, 
                                              @RequestBody Repas repas) {
        return repasRepository.findById(id)
                .map(existingRepas -> {
                    existingRepas.setNom(repas.getNom());
                    existingRepas.setPrix(repas.getPrix());
                    existingRepas.setType(repas.getType());
                    Repas updatedRepas = repasRepository.save(existingRepas);
                    return ResponseEntity.ok(updatedRepas);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprimer un repas
     * DELETE /repas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepas(@PathVariable Integer id) {
        if (repasRepository.existsById(id)) {
            repasRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
