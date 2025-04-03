package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.pojo.Delivery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper extends BaseMapper<Delivery> {
}
