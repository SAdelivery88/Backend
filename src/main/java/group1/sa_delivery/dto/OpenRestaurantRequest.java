package group1.sa_delivery.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OpenRestaurantRequest {
    @NotNull(message = "商家ID不能为空")
    private Integer seller_id;
    
    @NotBlank(message = "店铺名称不能为空")
    private String name;
    
    @NotBlank(message = "店铺地址不能为空")
    private String address;
    
    @NotBlank(message = "店铺电话不能为空")
    private String phone;
    
    @NotBlank(message = "营业时间不能为空")
    private String opening_hours;
}