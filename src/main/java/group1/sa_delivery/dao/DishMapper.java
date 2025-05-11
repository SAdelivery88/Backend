package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    /**
     * 根据 restaurantId 查询菜品信息
     *
     * @param restaurantId 餐厅 ID
     * @return Dish列表
     */
    @Select("SELECT * FROM dishes WHERE restaurant_id = #{restaurantId}")
    List<Dish> selectByRestaurantId(Integer restaurantId);
}
