package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.dto.RiderOrderResponse;
import group1.sa_delivery.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 根据卖家ID获取订单列表
     * @param sellerId 卖家ID
     * @return 订单响应列表
     */
    List<RiderOrderResponse> getOrdersBySellerId(@Param("seller_id") int sellerId);

    /**
     * selectByOrderId
     * @param orderId 订单ID
     * @return 订单信息
     */
    @Select("SELECT order_id,customer_id,restaurant_id,total_amount,status,created_at,delivered_at FROM orders WHERE order_id = #{orderId}")
    Order selectByOrderId(int orderId);

    /**
     * 查询所有订单
     * @return 订单响应列表
     */
    @Select("SELECT order_id, customer_id, restaurant_id, total_amount, status, DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') as created_at, DATE_FORMAT(delivered_at, '%Y-%m-%d %H:%i:%s') as delivered_at, rider_id FROM orders")
    List<RiderOrderResponse> getAllOrders();

    /**
     * 根据骑手ID获取订单列表
     * @param riderId 骑手ID
     * @return 订单响应列表
     */
    @Select("SELECT order_id, customer_id, restaurant_id, total_amount, status, DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') as created_at, DATE_FORMAT(delivered_at, '%Y-%m-%d %H:%i:%s') as delivered_at, rider_id FROM orders WHERE rider_id = #{riderId}")
    List<RiderOrderResponse> getOrdersByRiderId(@Param("riderId") int riderId);
}