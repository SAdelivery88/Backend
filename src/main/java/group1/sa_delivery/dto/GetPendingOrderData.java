package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import group1.sa_delivery.pojo.Order;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetPendingOrderData {
    @NotNull
    @JsonProperty("order_id")
    private Integer orderId;

    @NotNull
    @JsonProperty("customer_id")
    private Integer customerId;

    @NotNull
    @JsonProperty("restaurant_id")
    private Integer restaurantId;

    @NotNull
    @JsonProperty("total_amount")
    private Double totalAmount;

    @NotBlank
    private String status;

    @NotBlank
    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("estimated_time")
    private String estimatedTime;

    @JsonProperty("rider_id")
    private Integer riderId;

    private String phone;

    private String address;

    public static GetPendingOrderData fromOrder(Order order, String address) {
        GetPendingOrderData orderData = new GetPendingOrderData();
        orderData.setOrderId(order.getOrderId());
        orderData.setCustomerId(order.getCustomerId());
        orderData.setRestaurantId(order.getRestaurantId());
        orderData.setTotalAmount(order.getTotalAmount());
        orderData.setStatus(String.valueOf(order.getStatus()));
        orderData.setCreatedAt(String.valueOf(order.getCreatedAt()));
        orderData.setAddress(address);
        return orderData;
    }
}
