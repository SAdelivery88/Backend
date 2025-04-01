package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_details")
public class Order_detail {
    @TableId(type = IdType.AUTO)
    @NotNull(message = "订单详情id不能为空")
    @TableField("order_detail_id")
    private Integer detailId; //订单详情id

    @NotNull(message = "订单id不能为空")
    private Integer orderId; //订单id

    @NotNull(message = "菜品id不能为空")
    private Integer dishId; //菜品id

    @NotNull(message = "菜品数量不能为空")
    private Integer quantity; //菜品数量

    @NotNull(message = "菜品价格不能为空")
    private Double price; //菜品单价
}
