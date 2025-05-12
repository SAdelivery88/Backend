package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DishIdRequest {
    @JsonProperty("dish_id")
    private Integer dishId;
}
