package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.SellerService;
import group1.sa_delivery.dao.OrderMapper;
import group1.sa_delivery.Service.OrderService;
import group1.sa_delivery.dto.*;
import group1.sa_delivery.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @PostMapping("/openRestaurant")
    public ApiResponse<Void> openRestaurant(@RequestBody OpenRestaurantRequest request) {
        sellerService.createRestaurant(request);
        return ApiResponse.success("成功", null);
    }

    @PostMapping("/updateStatus")
    public ApiResponse<Void> updateRestaurantStatus(@RequestBody UpdateRestaurantStatusRequest request) {
        sellerService.updateRestaurantStatus(request);
        return ApiResponse.success("成功", null);
    }

    @GetMapping("/getOrderSeller")
    public ApiResponse<?> getOrderSeller(@RequestParam("user_id") int userId) {
            // 查询卖家的订单数据
            List<Order> orders = orderMapper.getOrdersBySellerId(userId);

            List<RiderOrderResponse> responses = orders.stream()
                    .map(RiderOrderResponse::convertToRiderOrderResponse)
                    .collect(Collectors.toList());

            if (orders == null || orders.isEmpty()) {
                return ApiResponse.error(400, "商家没有订单");
            }
            // 返回包含订单数据的响应
            return ApiResponse.success(200, "成功", responses);
    }

    /**
     * 商家接单
     * @param sellerOrderRequest
     * @return
     */
    @PostMapping("acceptOrderSeller")
    public ApiResponse<Void> receivingOrders(@RequestBody SellerOrderRequest sellerOrderRequest) {
        orderService.createOrder(sellerOrderRequest);
        return ApiResponse.success("接单成功",null);
    }
}