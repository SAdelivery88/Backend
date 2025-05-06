package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import group1.sa_delivery.pojo.Dish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommitDataInside {
    @JsonProperty("dish_id")
    private Integer dishId;
    @JsonProperty("restaurant_id")
    private Integer restaurantId;
    private String name;
    private Integer quantity;
    private String description;
    private Double price;
    private String category;
    private String status;

    public static CommitDataInside convertToCommitDataInside(Dish dish) {
        String status = String.valueOf((dish.getStatus()));
        return new CommitDataInside(
                dish.getDishId(),
                dish.getRestaurantId(),
                dish.getName(),
                0,
                dish.getDescription(),
                dish.getPrice(),
                dish.getCategory(),
                status
        );
    }
}
