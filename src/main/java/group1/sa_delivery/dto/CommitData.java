package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitData {
    private List<CommitDataInside> dishesInCart;
    @JsonProperty("total_amount")
    private Double totalAmount;
    @JsonProperty("order_id")
    private Integer orderId;
}
