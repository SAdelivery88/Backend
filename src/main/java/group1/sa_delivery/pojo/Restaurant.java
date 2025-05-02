package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("restaurants")
public class Restaurant {


    @TableId(type = IdType.AUTO)
    @NotNull(message = "餐厅id不能为空")
    private Integer restaurantId; //餐厅id

    @NotNull(message = "商户id不能为空")
    private Integer sellerId; //商户id

    @NotBlank(message = "餐厅名称不能为空")
    private String name; //餐厅名称

    @NotBlank(message = "餐厅地址不能为空")
    private String address; //餐厅地址

    @NotBlank(message = "餐厅电话不能为空")
    private String phone; //餐厅电话

    @TableField("opening_hours")
    private String openTime; //营业时间

    private RestaurantStatus status; //餐厅状态


}
