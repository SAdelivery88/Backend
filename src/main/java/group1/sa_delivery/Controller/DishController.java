package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.DishService;
import group1.sa_delivery.dao.DishMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.DishIdRequest;
import group1.sa_delivery.dto.DishRequest;
import group1.sa_delivery.pojo.Dish;
import group1.sa_delivery.pojo.DishStatus;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/seller")
@AllArgsConstructor
public class DishController {

    private final DishService dishService;

    /**
     * 添加菜品
     * @param dishRequest
     * @return
     */
    @PostMapping("/addDish")
    public ResponseEntity<ApiResponse<Void>> add(@RequestBody  @Valid DishRequest dishRequest) {
        return ResponseEntity.ok(dishService.addDish(dishRequest));
    }
    /**
     * 修改菜品
     * @param dishRequest
     * @return
     */
    @PostMapping("/modifyDish")
    public ResponseEntity<ApiResponse<Void>> update(@RequestBody @Valid DishRequest dishRequest) {
        return ResponseEntity.ok(dishService.updateDish(dishRequest));
    }

    /**
     * 下架菜品
     */
    @PostMapping("/unlistDish")
    public ResponseEntity<ApiResponse<Void>> unlist(@RequestBody DishIdRequest dishIdRequest) {
        return ResponseEntity.ok(dishService.unlistDish(dishIdRequest.getDishId()));
    }
    /**
     * 删除菜品
     * @param dish_id
     * @return
     */
    @DeleteMapping("/deleteDish")
    public ResponseEntity<ApiResponse<Void>> deleteDish(@RequestParam Integer dish_id) {
        return ResponseEntity.ok(dishService.deleteDish(dish_id));
    }

    /**
     * 商品售空
     */
    @PostMapping("/soldoutDish")
    public ResponseEntity<ApiResponse<Void>> sellerEmpty(@RequestBody DishIdRequest dishIdRequest) {
        return ResponseEntity.ok(dishService.soldOutDish(dishIdRequest.getDishId()));
    }
}

