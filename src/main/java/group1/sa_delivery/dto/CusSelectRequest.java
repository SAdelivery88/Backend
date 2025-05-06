package group1.sa_delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CusSelectRequest {
    @JsonProperty("seller_id")
    private Integer sellerId;
}
