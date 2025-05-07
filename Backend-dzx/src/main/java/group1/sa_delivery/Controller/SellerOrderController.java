package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.OrderService;
import group1.sa_delivery.dao.DishMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.SellerOrderRequest;
import group1.sa_delivery.pojo.Dish;
import group1.sa_delivery.pojo.DishStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("seller")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishMapper dishMapper;
    /**
     * 商家接单
     * @param sellerOrderRequest
     * @return
     */
    @PostMapping("acceptOrderSeller")
    public ApiResponse<Void> receivingOrders(@RequestBody SellerOrderRequest sellerOrderRequest) {

        try {
            orderService.createOrder(sellerOrderRequest);
        } catch (Exception e) {
            e.printStackTrace();
           return ApiResponse.error(400, e.getMessage());
        }
        return ApiResponse.success("接单成功",null);
    }

    /**
     * 商品售空
     * @param dish
     * @return
     */
    @PostMapping("soldoutDish")
    public ApiResponse<Void> sellerEmpty(@RequestBody @Validated(value = {Dish.UnList.class})  Dish dish) {


        Dish dishResult = dishMapper.selectById(dish.getDishId());
        if(null != dishResult) {
            if(dishResult.getStatus().equals(DishStatus.AVAILABLE)) {
                return ApiResponse.success(null,null);
            }else{
                return ApiResponse.error(400,"商品下架或者已售空");
            }
        }
        return ApiResponse.error(400,"未找到商品信息");
    }


}
