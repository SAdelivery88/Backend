package group1.sa_delivery.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SellerOrderResponse {
    private Integer orderId;
    private Integer customerId;
    private Integer restaurantId;
    private BigDecimal totalAmount;
    private String status;
    @JsonProperty("create_at")
    private String createdAt;
    @JsonProperty("delivery_at")
    private String deliveredAt;
    private Integer riderId;

}