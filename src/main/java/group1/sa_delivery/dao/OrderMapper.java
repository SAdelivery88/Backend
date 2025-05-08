package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.dto.RiderOrderResponse;
import group1.sa_delivery.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * selectByOrderId
     *
     * */
    @Select("SELECT order_id,customer_id,restaurant_id,total_amount,status,created_at,delivered_at FROM orders WHERE order_id = #{orderId}")
    Order selectByOrderId(int orderId);

    @Select("SELECT o.order_id, o.customer_id, o.restaurant_id, o.total_amount, o.status, o.created_at, o.delivered_at, o.rider_id\n" +
            "        FROM orders o\n" +
            "                 JOIN restaurants r ON o.restaurant_id = r.restaurant_id\n" +
            "        WHERE r.seller_id = #{seller_id}")
    List<Order> getOrdersBySellerId(int seller_id);

    @Select("Select * from orders")
    List<Order> getAllOrders();

    @Select("SELECT o.order_id, o.customer_id, o.restaurant_id, o.total_amount, o.status, o.created_at, o.delivered_at, o.rider_id\n" +
            "        FROM orders o\n" +
            "        WHERE o.rider_id = #{rider_id}")
    List<Order> getOrdersByRiderId(int rider_id);
}
