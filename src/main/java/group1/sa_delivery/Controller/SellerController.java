package group1.sa_delivery.Controller;

import group1.sa_delivery.Security.userDetailService;
import group1.sa_delivery.dao.OrderMapper;
import group1.sa_delivery.dto.*;
import group1.sa_delivery.Service.SellerService;
import group1.sa_delivery.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping("/openRestaurant")
    public ApiResponse<Void> openRestaurant(@RequestBody OpenRestaurantRequest request) {
        try {
            sellerService.createRestaurant(request);
            return ApiResponse.success("success", null);
        } catch (Exception e) {
            return ApiResponse.error(0, e.getMessage());
        }
    }

    @PostMapping("/updateStatus")
    public ApiResponse<Void> updateRestaurantStatus(@RequestBody UpdateRestaurantStatusRequest request) {
        try {
            sellerService.updateRestaurantStatus(request);
            return ApiResponse.success("success", null);
        } catch (Exception e) {
            return ApiResponse.error(0, e.getMessage());
        }
    }

    @GetMapping("/getOrderSeller")
    public ApiResponse<?> getOrderSeller(@RequestParam("user_id") int userId) {
        try {
            // 查询卖家的订单数据
            List<RiderOrderResponse> orders = orderMapper.getOrdersBySellerId(userId);

            if (orders == null || orders.isEmpty()) {
                return ApiResponse.error(400, "No orders found for the seller");
            }
            // 返回包含订单数据的响应
            return ApiResponse.success(200, "Success", orders);
        } catch (Exception e) {
            // 处理查询订单时可能出现的异常
            return ApiResponse.error(400, "Error occurred while fetching orders: " + e.getMessage());
        }
    }
}