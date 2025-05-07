package group1.sa_delivery.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RiderOrderResponse {
    private Integer orderId;
    private Integer customerId;
    private Integer restaurantId;
    private BigDecimal totalAmount;
    private String status;
    private String createdAt;
    private String deliveredAt;
    private Integer riderId;
}