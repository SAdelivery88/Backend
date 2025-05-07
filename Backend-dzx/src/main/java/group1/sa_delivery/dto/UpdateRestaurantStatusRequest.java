package group1.sa_delivery.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UpdateRestaurantStatusRequest {
    @NotNull(message = "餐厅ID不能为空")
    private Integer restaurant_id;
}