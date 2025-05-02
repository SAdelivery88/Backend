package group1.sa_delivery.Service;

import group1.sa_delivery.dao.DishMapper;
import group1.sa_delivery.dao.RestaurantMapper;
import group1.sa_delivery.dao.UserMapper;
import group1.sa_delivery.dto.CusSelectData;
import group1.sa_delivery.dto.CusSelectRequest;
import group1.sa_delivery.dto.GetRestaurantsData;
import group1.sa_delivery.pojo.Dish;
import group1.sa_delivery.pojo.Restaurant;
import group1.sa_delivery.pojo.Role;
import group1.sa_delivery.pojo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RestaurantMapper restaurantMapper;
    private final DishMapper dishMapper;
    private final UserMapper userMapper;

    /**
     * 用户选择商家并返回商家菜品列表
     * @param request 请求体
     * @return CusSelectData 列表
     */
    public List<CusSelectData> cusSelectStore(CusSelectRequest request) {
        Integer sellerId = request.getSellerId();
        User user = userMapper.selectById(sellerId);
        if(user.getRole() != Role.SELLER){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Id is not a seller");
        }

        Restaurant curRestaurant;
        try {
            // 查询餐厅
            curRestaurant = restaurantMapper.selectBySellerId(sellerId);
            if (curRestaurant == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid seller ID");
        }

        Integer restaurantId = curRestaurant.getRestaurantId();
        List<Dish> dishes = dishMapper.selectByRestaurantId(restaurantId);

        return convertDishesToCusSelectData(dishes);


    }

    /**
     * 将 Dish 列表转换为 CusSelectData 列表
     * @param dishes 菜品列表
     * @return CusSelectData 列表
     */
    private List<CusSelectData> convertDishesToCusSelectData(List<Dish> dishes) {
        return dishes.stream()
                .map(dish -> {
                    return CusSelectData.builder()
                            .dishId(dish.getDishId())
                            .name(dish.getName())
                            .description(dish.getDescription())
                            .price(dish.getPrice())
                            .category(dish.getCategory())
                            .status(dish.getStatus() != null ? dish.getStatus().toString() : null)
                            .build();
                })
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * customer get restaurants whose status = 'open'
     * @return List of GetRestaurantsData objects matching the search criteria
     */
    public List<GetRestaurantsData> getRestaurants() {
        List<Restaurant> restaurants = restaurantMapper.selectAllOpenRestaurants();
        if(restaurants.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No restaurants found");
        }

        return convertRestaurantToGetRestaurantsData(restaurants);
    }

    private List<GetRestaurantsData> convertRestaurantToGetRestaurantsData(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> {
                    return GetRestaurantsData.builder()
                            .restaurantId(restaurant.getRestaurantId())
                            .sellerId(restaurant.getSellerId())
                            .name(restaurant.getName())
                            .address(restaurant.getAddress())
                            .phone(restaurant.getPhone())
                            .openTime(restaurant.getOpenTime())
                            .status(String.valueOf(restaurant.getStatus()))
                            .build();
                })
                .collect(java.util.stream.Collectors.toList());
    }
}
