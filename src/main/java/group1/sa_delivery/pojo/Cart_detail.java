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
@TableName("cart_details")
public class Cart_detail {
    @TableId(value = "cart_detail_id", type = IdType.AUTO)
    @NotNull(message = "购物车详情id不能为空")
    private Integer detailId; //购物车详情id

    @NotNull(message = "购物车id不能为空")
    private Integer cartId; //购物车id

    @NotNull(message = "菜品id不能为空")
    private Integer dishId; //菜品id

    @NotNull(message = "菜品数量不能为空")
    private Integer quantity; //菜品数量

    @NotNull(message = "菜品价格不能为空")
    private Double price; //菜品单价
}
