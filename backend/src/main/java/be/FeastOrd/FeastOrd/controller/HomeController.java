package be.FeastOrd.FeastOrd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class HomeController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bienvenue sur l'API FeastOrd");
        response.put("status", "OK");
        response.put("endpoints", Map.of(
            "restaurants", "/restaurants",
            "menus", "/menus",
            "reservations", "/reservations"
        ));
        return ResponseEntity.ok(response);
    }
}
