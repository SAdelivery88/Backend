package group1.sa_delivery.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import group1.sa_delivery.dao.OrderMapper;
import group1.sa_delivery.dao.Order_detailMapper;
import group1.sa_delivery.dao.UserMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.SellerOrderRequest;
import group1.sa_delivery.pojo.Order;
import group1.sa_delivery.pojo.OrderStatus;
import group1.sa_delivery.pojo.Order_detail;
import group1.sa_delivery.pojo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final Order_detailMapper orderDetailMapper;

    private final UserMapper userMapper;
    @Transactional
    public ApiResponse<Void> createOrder(SellerOrderRequest sellerOrderRequest) {

        Double sum = 0.0;
        //计算总价
        for (Order_detail orderDetail : sellerOrderRequest.getOrderDetailList()) {
            sum = sum + orderDetail.getPrice()*orderDetail.getQuantity();
        }

        Order order = new Order();
        order.setCustomerId(sellerOrderRequest.getCustomerId());
        order.setRestaurantId(sellerOrderRequest.getRestaurantId());
        order.setTotalAmount(sum);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setCreateAt(LocalDateTime.now());
        orderMapper.insert(order);

        sellerOrderRequest.getOrderDetailList().forEach(orderDetail -> {
            orderDetail.setOrderId(order.getOrderId());
            orderDetailMapper.insert(orderDetail);
        });

        return ApiResponse.success("接单成功",null);
    }
}
