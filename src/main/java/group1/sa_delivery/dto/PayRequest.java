package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PayRequest {
    @NotNull
    @JsonProperty("order_id")
    private Integer orderId;
}
