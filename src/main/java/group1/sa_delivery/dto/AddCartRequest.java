package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddCartRequest {
    @NotNull
    @JsonProperty("dish_id")
    private Integer dishId;
    @NotNull
    private Integer number;
}
