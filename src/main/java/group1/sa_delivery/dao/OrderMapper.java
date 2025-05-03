package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 重写selectByOrderId
     *
     * */
    @Select("SELECT order_id,customer_id,restaurant_id,total_amount,status,created_at,delivered_at FROM orders WHERE order_id = #{orderId}")
    Order selectByOrderId(int orderId);
}
