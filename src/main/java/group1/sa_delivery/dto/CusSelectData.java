package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CusSelectData {
    @JsonProperty("dish_id")
    private Integer dishId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String status;
}
