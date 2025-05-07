package group1.sa_delivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderDetailsRequest {
    @NotNull(message = "菜品id不能为空")
    Integer dishId;

    @NotNull(message = "菜品数量不能为空")
    private Integer quantity; //菜品数量

    @NotNull(message = "菜品价格不能为空")
    private Double price; //菜品单价
}
