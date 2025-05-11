package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import group1.sa_delivery.pojo.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchData {
    @JsonProperty("restaurant_id")
    private Integer restaurantId;
    @JsonProperty("seller_id")
    private Integer sellerId;
    private String name;
    private String address;
    private String phone;
    @JsonProperty("opening_hours")
    private String openTime;
    private String status;

    public static SearchData convertToSearchData(Restaurant restaurant) {
        String status = String.valueOf((restaurant.getStatus()));
        return new SearchData(
                restaurant.getRestaurantId(),
                restaurant.getSellerId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhone(),
                restaurant.getOpenTime(),
                status
            );
    }
}


