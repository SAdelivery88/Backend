package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.pojo.Restaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RestaurantMapper extends BaseMapper<Restaurant> {
    /**
     * 根据 sellerId 查询餐厅信息
     *
     * @param sellerId 卖家 ID
     * @return 餐厅对象
     */
    @Select("SELECT * FROM restaurants WHERE seller_id = #{sellerId}")
    Restaurant selectBySellerId(Integer sellerId);

    /**
     * 查询所有营业中的餐厅
     *
     * @return 餐厅列表
     */
    @Select("SELECT restaurant_id,seller_id,name,address,phone,opening_hours AS openTime,status FROM restaurants")
    List<Restaurant> selectAllOpenRestaurants();
}
