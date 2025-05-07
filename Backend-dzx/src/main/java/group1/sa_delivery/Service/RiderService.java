package group1.sa_delivery.Service;

import group1.sa_delivery.dao.OrderMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.RiderOrderRequest;
import group1.sa_delivery.dto.RiderOrderResponse;
import group1.sa_delivery.pojo.Order;
import group1.sa_delivery.pojo.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiderService {
    private final OrderMapper orderMapper;
    /**
     * rider accept the order
     * @param request containing order_id
     *
     * */
    public ApiResponse<Void> acceptOrderRider(RiderOrderRequest request){
        Integer orderId = request.getOrderId();
        Order curOrder = orderMapper.selectByOrderId(orderId);
        if(curOrder == null){
            return ApiResponse.error(400,"Order not found");
        }
        if(curOrder.getStatus() != OrderStatus.PREPARING){
            return ApiResponse.error(400,"Order isn't PREPARING to accept");
        }

        curOrder.setStatus(OrderStatus.DELIVERING);
        orderMapper.updateById(curOrder);

        return ApiResponse.success("Change the order status to DELIVERING",null);
    }
    /**
     * the order is delivered
     * @param request containing order_id
     *
     * */
    public ApiResponse<Void> deliveredOrderRider(RiderOrderRequest request){
        Integer orderId = request.getOrderId();
        Order curOrder = orderMapper.selectByOrderId(orderId);
        if(curOrder == null){
            return ApiResponse.error(400,"Order not found");
        }
        if(curOrder.getStatus() != OrderStatus.DELIVERING){
            return ApiResponse.error(400,"Order isn't DELIVERING");
        }

        curOrder.setStatus(OrderStatus.COMPLETED);
        orderMapper.updateById(curOrder);

        return ApiResponse.success("Change the order status to COMPLETED",null);
    }
    /**
     * 骑手查看所有订单
     * @return 包含订单数据的响应
     */
    public ApiResponse<List<RiderOrderResponse>> askOrderRider() {
        List<RiderOrderResponse> orders = orderMapper.getAllOrders();
        if (orders.isEmpty()) {
            return ApiResponse.error(400, "No orders found");
        }
        return ApiResponse.success(200, "Success", orders);
    }
    /**
     * 骑手查看自己的订单
     * @param riderId 骑手ID
     * @return 包含订单数据的响应
     */
    public ApiResponse<List<RiderOrderResponse>> askMyOrderRider(int riderId) {
        List<RiderOrderResponse> orders = orderMapper.getOrdersByRiderId(riderId);
        if (orders.isEmpty()) {
            return ApiResponse.error(400, "No orders found for the rider");
        }
        return ApiResponse.success(200, "Success", orders);
    }
}
