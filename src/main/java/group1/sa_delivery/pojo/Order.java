package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    @NotNull(message = "订单id不能为空")
    private Integer orderId;    //订单id

    @NotNull(message = "顾客id不能为空")
    private Integer customerId; //顾客id

    @NotNull(message = "餐厅id不能为空")
    private Integer restaurantId;    //餐厅id

    @NotNull(message = "总价不能为空")
    private Double totalAmount;    //总价

    private OrderStatus status;    //订单状态

    private LocalDateTime createAt; //创建时间

    private LocalDateTime deliveredAt;  //送达时间
}
