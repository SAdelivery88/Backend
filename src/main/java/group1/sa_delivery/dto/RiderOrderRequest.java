package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RiderOrderRequest {
    @JsonProperty("order_id")
    private Integer orderId;
}
