package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import group1.sa_delivery.pojo.Order;
import group1.sa_delivery.pojo.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderOrderResponse {
    @JsonProperty("order_id")
    private Integer orderId;
    @JsonProperty("customer_id")
    private Integer customerId;
    @JsonProperty("restaurant_id")
    private Integer restaurantId;
    @JsonProperty("total_amount")
    private Double totalAmount;
    private String status;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("delivered_at")
    private String deliveredAt;
    @JsonProperty("rider_id")
    private Integer riderId;

    public static RiderOrderResponse convertToRiderOrderResponse(Order order) {
        return new RiderOrderResponse(
                order.getOrderId(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getTotalAmount(),
                String.valueOf(order.getStatus()),
                String.valueOf(order.getCreatedAt()),
                String.valueOf(order.getDeliveredAt()),
                order.getRiderId()
        );
    }
}