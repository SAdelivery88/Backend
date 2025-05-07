package group1.sa_delivery.Controller;

import group1.sa_delivery.dao.DishMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.pojo.Dish;
import group1.sa_delivery.pojo.DishStatus;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("seller")
@AllArgsConstructor
public class DishController {

    private final DishMapper dishMapper;

    /**
     * 添加菜品
     * @param dish
     * @return
     */
    @PostMapping("addDish")
    public ApiResponse<Void> add(@RequestBody  @Validated(value = {Dish.Add.class})  Dish dish) {
        try {
            dishMapper.insert(dish);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("添加失败");
        }
        return ApiResponse.success("添加成功",null);
    }
    /**
     * 修改菜品
     * @param dish
     * @return
     */
    @PostMapping("modifyDish")
    public ApiResponse<Void> update(@RequestBody @Validated(value = {Dish.Update.class}) Dish dish) {
        try {
            dishMapper.updateById(dish);
        } catch (Exception e) {
            return ApiResponse.error("修改失败");
        }
        return ApiResponse.success("修改成功",null);
    }

    /**
     * 下架菜品
     * @param dish
     * @return
     */
    @PostMapping("unlistDish")
    public ApiResponse<Void> unlist(@RequestBody @Validated(value = {Dish.UnList.class}) Dish dish) {
        try {
            dish.setStatus(DishStatus.UNLIST);
            dishMapper.updateById(dish);
        } catch (Exception e) {
            return ApiResponse.error("下架失败");
        }
        return ApiResponse.success("下架成功",null);
    }
    /**
     * 删除菜品
     * @param dish_id
     * @return
     */
    @DeleteMapping("deleteDish")
    public ApiResponse<Void> delete(@RequestParam Integer dish_id) {
        try {
            dishMapper.deleteById(dish_id);
        } catch (Exception e) {
            return ApiResponse.error("删除失败");
        }
        return ApiResponse.success("删除成功",null);
    }


}

