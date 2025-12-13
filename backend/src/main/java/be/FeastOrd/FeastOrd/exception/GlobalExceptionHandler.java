package be.FeastOrd.FeastOrd.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError; // Important
import org.springframework.web.bind.MethodArgumentNotValidException; // Important
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> 
        { // On parcourt toutes les erreurs (ex: "mail invalide", "nom vide")
            String fieldName = ((FieldError) error).getField(); // mail
            String errorMessage = error.getDefaultMessage();    //Format invalide
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity.badRequest().body(errors);// On renvoie une Map JSON
    }
}