package group1.sa_delivery.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group1.sa_delivery.dao.DishMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.DishRequest;
import group1.sa_delivery.pojo.Dish;
import group1.sa_delivery.pojo.DishStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishMapper dishMapper;
    /**
    * 添加菜品
    * */
    public ApiResponse<Void> addDish(DishRequest dishRequest){
        if(dishRequest.getPrice()<=0){
            return ApiResponse.error("价格不能为空");
        }
        if(dishRequest.getName().isEmpty()){
            return ApiResponse.error("菜品名不能为空");
        }
        Dish dish = new Dish();
        dish.setName(dishRequest.getName());
        dish.setDescription(dishRequest.getDescription());
        dish.setPrice(dishRequest.getPrice());
        dish.setCategory(dishRequest.getCategory());
        dish.setRestaurantId(dishRequest.getRestaurantId());
        dishMapper.insert(dish);
        return ApiResponse.success("添加成功",null);
    }
    /**
     * 更新菜品
    * */
    public ApiResponse<Void> updateDish(DishRequest dishRequest){
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        wrapper.eq("name", dishRequest.getName());
        Dish dish = dishMapper.selectOne(wrapper);
        if(dish == null){
            return ApiResponse.error("找不到菜品");
        }
        dish.setDescription(dishRequest.getDescription());
        dish.setPrice(dishRequest.getPrice());
        dish.setCategory(dishRequest.getCategory());
        dishMapper.updateById(dish);
        return ApiResponse.success("更新成功",null);
    }

    /**
     * 设置菜品状态为已售空
     * */
    public ApiResponse<Void> soldOutDish(Integer dishId){
        Dish dish = dishMapper.selectById(dishId);
        if(dish == null){
            return ApiResponse.error("找不到菜品");
        }
        dish.setStatus(DishStatus.SOLD_OUT);
        dishMapper.updateById(dish);
        return ApiResponse.success("已更新菜品状态为已售空",null);
    }

    /**
     * 删除菜品
     * */
    public ApiResponse<Void> deleteDish(Integer dishId){
        Dish dish = dishMapper.selectById(dishId);
        if(dish == null){
            return ApiResponse.error("找不到菜品");
        }

        dishMapper.deleteById(dishId);
        return ApiResponse.success("已删除菜品",null);
    }

    /**
     * 下架菜品
     * */
    public ApiResponse<Void> unlistDish(Integer dishId){
        Dish dish = dishMapper.selectById(dishId);
        if(dish == null){
            return ApiResponse.error("找不到菜品");
        }

        dish.setStatus(DishStatus.UNLIST);
        dishMapper.updateById(dish);
        return ApiResponse.success("下架菜品成功",null);
    }
}
