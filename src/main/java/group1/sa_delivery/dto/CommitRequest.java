package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommitRequest {
    @NotNull
    @JsonProperty("restaurant_id")
    private Integer restaurantId;
}
