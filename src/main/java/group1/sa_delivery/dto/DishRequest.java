package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DishRequest {

    private String name;
    private String description;
    private Double price;
    private String category;
    @JsonProperty("restaurant_id")
    private Integer restaurantId;
}
