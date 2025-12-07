package be.FeastOrd.FeastOrd.dto;

import lombok.Data;

@Data
public class MenuRequest {
    private Integer restaurantId;
    private Integer entreeId;
    private Integer repasId;
    private Integer dessertId;
}

