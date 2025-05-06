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
public class GetRestaurantsData {
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
}
