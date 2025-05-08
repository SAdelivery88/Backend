package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.SellerService;
import group1.sa_delivery.dao.OrderMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.OpenRestaurantRequest;
import group1.sa_delivery.dto.RiderOrderResponse;
import group1.sa_delivery.dto.UpdateRestaurantStatusRequest;
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
            List<Order> orders = orderMapper.getOrdersBySellerId(userId);

            List<RiderOrderResponse> responses = orders.stream()
                    .map(RiderOrderResponse::convertToRiderOrderResponse)
                    .collect(Collectors.toList());

            if (orders == null || orders.isEmpty()) {
                return ApiResponse.error(400, "No orders found for the seller");
            }
            // 返回包含订单数据的响应
            return ApiResponse.success(200, "Success", responses);
        } catch (Exception e) {
            // 处理查询订单时可能出现的异常
            return ApiResponse.error(400, "Error occurred while fetching orders: " + e.getMessage());
        }
    }
}