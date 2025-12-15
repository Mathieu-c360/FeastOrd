package be.FeastOrd.FeastOrd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String error;
    private int status;

    // Constructeur explicite pour éviter les problèmes avec Lombok + Java 25
    public ErrorResponse(String message, String error, int status) {
        this.message = message;
        this.error = error;
        this.status = status;
    }


    // Getters et setters manuels pour Java 25 + compatibilité JSON (Jackson)
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }



}