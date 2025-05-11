package group1.sa_delivery.dto;

import group1.sa_delivery.pojo.Order_detail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SellerOrderRequest {
    @NotNull(message = "顾客id不能为空")
    Integer customerId;
    @NotNull(message = "餐厅id不能为空")
    Integer restaurantId;

    List<Order_detail> orderDetailList;
}
