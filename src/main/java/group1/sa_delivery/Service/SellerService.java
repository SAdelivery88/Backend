package group1.sa_delivery.Service;

import group1.sa_delivery.dto.OpenRestaurantRequest;
import group1.sa_delivery.dao.RestaurantMapper;
import group1.sa_delivery.dto.UpdateRestaurantStatusRequest;
import group1.sa_delivery.pojo.Restaurant;
import group1.sa_delivery.pojo.RestaurantStatus;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final RestaurantMapper restaurantMapper;

    public SellerService(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    public void createRestaurant(OpenRestaurantRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setSellerId(request.getSeller_id());
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setOpenTime(request.getOpening_hours());
        restaurant.setStatus(RestaurantStatus.OPEN);

        if (restaurantMapper.insert(restaurant) <= 0) {
            throw new RuntimeException("创建店铺失败");
        }
    }

    public void updateRestaurantStatus(UpdateRestaurantStatusRequest request) {
        Restaurant restaurant = restaurantMapper.selectById(request.getRestaurant_id());
        if(restaurant == null){
            throw new RuntimeException("未找到该餐厅");
        }
        if(restaurant.getStatus() == RestaurantStatus.OPEN){
            restaurant.setStatus(RestaurantStatus.CLOSE);
        }else{
            restaurant.setStatus(RestaurantStatus.OPEN);
        }
        if(restaurantMapper.updateById(restaurant) <= 0){
            throw new RuntimeException("更新餐厅状态失败");
        }
    }
}