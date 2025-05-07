package group1.sa_delivery.Service;

import group1.sa_delivery.Security.userDetailService;
import group1.sa_delivery.dao.DeliveryMapper;
import group1.sa_delivery.dao.OrderMapper;
import group1.sa_delivery.dao.UserMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.RiderOrderRequest;
import group1.sa_delivery.pojo.Delivery;
import group1.sa_delivery.pojo.Order;
import group1.sa_delivery.pojo.OrderStatus;
import group1.sa_delivery.pojo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class RiderService {
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final DeliveryMapper deliveryMapper;
    private final userDetailService userdetailService;
    /**
     * rider accept the order
     * @param request containing order_id
     *
     * */
    @Transactional
    public ApiResponse<Void> acceptOrderRider(RiderOrderRequest request){
        User curRider = userdetailService.getCurrentUser();

        Integer orderId = request.getOrderId();
        Order curOrder = orderMapper.selectByOrderId(orderId);
        if(curOrder == null){
            return ApiResponse.error(400,"Order not found");
        }

        Delivery delivery = deliveryMapper.selectByOrderId(orderId);
        if(delivery != null){
            return ApiResponse.error(400,"Order's Delivery already exists");
        }
        // 检查订单状态是否允许接单
        if(curOrder.getStatus() != OrderStatus.PREPARING){
            return ApiResponse.error(400,"Order isn't PREPARING to accept");
        }

        // 计算预计送达时间(Order创建加30min）
        LocalDateTime estimatedTime = curOrder.getCreatedAt().plusMinutes(30);

        // 获取Order中顾客的地址
        Integer curCustomerId = curOrder.getCustomerId();
        User curOrderCustomer = userMapper.selectById(curCustomerId);
        if(curOrderCustomer == null){
            return ApiResponse.error(400,"Customer of Order is not found");
        }
        String orderAddress = curOrderCustomer.getAddress();

        // 创建并填充配送记录
        Delivery curDelivery = new Delivery();
        curDelivery.setOrderId(orderId);
        curDelivery.setRiderId(curRider.getUserId());
        //Phone存入骑手的电话
        curDelivery.setPhone(curRider.getPhone());
        curDelivery.setAddress(orderAddress);
        curDelivery.setEstimatedTime(estimatedTime) ;

        // 插入配送记录
        int insertResult = deliveryMapper.insert(curDelivery);
        if (insertResult <= 0) {
            return ApiResponse.error(400,"Failed to insert delivery record");
        }

        curOrder.setStatus(OrderStatus.DELIVERING);
        int updateResult = orderMapper.updateById(curOrder);
        if (updateResult <= 0) {
            return ApiResponse.error(400,"Failed to update order status");
        }

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
        int updateResult = orderMapper.updateById(curOrder);
        if(updateResult <= 0){
            return ApiResponse.error(400,"Failed to update order status");
        }
        return ApiResponse.success("Change the order status to COMPLETED",null);
    }
}
