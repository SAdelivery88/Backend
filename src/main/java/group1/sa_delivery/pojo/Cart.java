package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("carts")
public class Cart
{
    @TableId(type = IdType.AUTO)
    @NotNull(message = "购物车id不能为空")
    private Integer cartId;    //购物车id

    @NotNull(message = "顾客id不能为空")
    private Integer userId; //顾客id

    @NotNull(message = "餐厅id不能为空")
    private Integer restaurantId; //餐馆id

    @NotNull(message = "总价不能为空")
    private Double totalAmount; //总价
}
