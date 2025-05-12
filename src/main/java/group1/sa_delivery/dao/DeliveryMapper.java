package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.pojo.Delivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DeliveryMapper extends BaseMapper<Delivery> {

    @Select("SELECT * FROM deliveries WHERE order_id = #{orderId}")
    Delivery selectByOrderId(int orderId);
}
