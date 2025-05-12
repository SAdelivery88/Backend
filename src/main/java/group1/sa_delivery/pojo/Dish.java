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
@TableName("dishes")
public class Dish {
    @TableId(type = IdType.AUTO)
    @NotNull(message = "菜品id不能为空")
    private Integer dishId;     //菜品id

    @NotNull(message = "餐厅id不能为空")
    @TableField("restaurant_id")
    private Integer restaurantId;//餐厅id

    @NotBlank(message = "菜品名称不能为空")
    private String name;        //菜品名称

    private String description; //菜品描述

    @NotBlank(message = "菜品价格不能为空")
    private Double price;       //菜品价格

    private String category;    //菜品类别(不一定用得上)

    private DishStatus status;  //菜品状态

}
