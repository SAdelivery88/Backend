package group1.sa_delivery.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group1.sa_delivery.dao.*;
import group1.sa_delivery.dto.*;
import group1.sa_delivery.pojo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RestaurantMapper restaurantMapper;
    private final DishMapper dishMapper;
    private final UserMapper userMapper;
    private final CartMapper cartMapper;
    private final Cart_detailMapper cartDetailMapper;
    private final group1.sa_delivery.Security.userDetailService userDetailService;
    private final OrderMapper orderMapper;
    private final DeliveryMapper deliveryMapper;

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
    @Transactional
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
                            .openTime(restaurant.getOpenTime() != null ? restaurant.getOpenTime() : "")
                            .status(String.valueOf(restaurant.getStatus()))
                            .build();
                })
                .collect(java.util.stream.Collectors.toList());
    }

    private boolean existsByCartIdAndDishId(Integer cartId, Integer dishId) {
        QueryWrapper<Cart_detail> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("cart_id", cartId)
                .eq("dish_id", dishId);
        return cartDetailMapper.selectCount(queryWrapper) > 0;
    }

    public ApiResponse<Void> addToCart(AddCartRequest request) {
        Integer dishId = request.getDishId();
        Integer quantity = request.getNumber();
        User currentUser = userDetailService.getCurrentUser();
        //User currentUser = userMapper.selectById(1);
        Dish dish = dishMapper.selectById(dishId);
        Integer restaurant = dish.getRestaurantId();
        Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>().eq("user_id", currentUser.getUserId())
                                                                .eq("restaurant_id", restaurant));

        // 检查菜品是否上架
        if (dish.getStatus() != DishStatus.AVAILABLE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dish is not available");
        }
        // 检查数量是否合法
        if (quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid quantity");
        }

        if(cart == null){
            // 创建购物车
            cart = new Cart();
            cart.setUserId(currentUser.getUserId());
            cart.setRestaurantId(restaurant);
            cart.setTotalAmount((double) 0);
            cartMapper.insert(cart);
        }
        else{
            // 检查菜品是否已在购物车中
            if (existsByCartIdAndDishId(cart.getCartId(), dishId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dish is already in cart");
            }
        }

        // 添加菜品到购物车
        Cart_detail cart_detail = new Cart_detail();
        cart_detail.setCartId(cart.getCartId());
        cart_detail.setDishId(dishId);
        cart_detail.setQuantity(quantity);
        cart_detail.setPrice(dish.getPrice());
        cartDetailMapper.insert(cart_detail);
        cart.setTotalAmount(cart.getTotalAmount() + dish.getPrice() * quantity);
        cartMapper.updateById(cart);
        return ApiResponse.success("Add to cart successfully", null);
    }

    public ApiResponse<CommitData> commit(CommitRequest request) {
        Integer restaurantId = request.getRestaurantId();
        User currentUser = userDetailService.getCurrentUser();
        Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>().eq("user_id", currentUser.getUserId())
                .eq("restaurant_id", restaurantId));

        // 检查购物车是否存在
        if (cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }
        Restaurant restaurant = restaurantMapper.selectById(restaurantId);
        List<Cart_detail> cart_details = cartDetailMapper.selectList(
                new QueryWrapper<Cart_detail>().eq("cart_id", cart.getCartId()));
        if (cart_details.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is empty");
        }

        // 提交订单
        List<Integer> dishIds = cart_details.stream()
                .map(Cart_detail::getDishId)
                .collect(Collectors.toList());
        List<Dish> dishes = dishMapper.selectBatchIds(dishIds);
        Map<Integer, Integer> dishQuantityMap = cart_details.stream()
                .collect(Collectors.toMap(
                        Cart_detail::getDishId,
                        Cart_detail::getQuantity
                ));
        List<CommitDataInside> commitDataList = dishes.stream()
                .map(dish -> {
                    CommitDataInside data = CommitDataInside.convertToCommitDataInside(dish);

                    Integer quantity = dishQuantityMap.get(dish.getDishId());
                    data.setQuantity(quantity);

                    return data;
                })
                .collect(Collectors.toList());
        CommitData commitData = new CommitData();
        commitData.setDishesInCart(commitDataList);
        commitData.setTotalAmount(cart.getTotalAmount());

        Order order = new Order();
        order.setCustomerId(currentUser.getUserId());
        order.setRestaurantId(restaurant.getRestaurantId());
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        orderMapper.insert(order);
        commitData.setOrderId(order.getOrderId());

        cartMapper.deleteById(cart.getCartId());
        cartDetailMapper.delete(new QueryWrapper<Cart_detail>().eq("cart_id", cart.getCartId()));

        return ApiResponse.success("Commit successfully", commitData);
    }

    public ApiResponse<Void> cancel(Integer orderId){
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        order.setStatus(OrderStatus.CANCELED);
        orderMapper.updateById(order);
        return ApiResponse.success("Cancel successfully", null);
    }

    public ApiResponse<Void> pay(PayRequest request){
        Integer orderId = request.getOrderId();
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        orderMapper.updateById(order);
        return ApiResponse.success("Pay successfully", null);
    }

    public ApiResponse<List<GetPendingOrderData>> askPendingOrder(Integer customerId) {
        List<Order> orders = orderMapper.selectList(
                new QueryWrapper<Order>()
                        .eq("customer_id", customerId)
                        .in("status",
                                Arrays.asList(
                                        OrderStatus.PENDING,
                                        OrderStatus.CONFIRMED,
                                        OrderStatus.PREPARING,
                                        OrderStatus.DELIVERING
                                )
                        )
        );
        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pending order found");
        }

        String address = userMapper.selectById(customerId).getAddress();
        List<GetPendingOrderData> orderDataList = orders.stream().map(order -> {
            // 基础订单数据转换
            GetPendingOrderData data = GetPendingOrderData.fromOrder(order, address);

            // 仅处理配送中的订单
            if (order.getStatus() == OrderStatus.DELIVERING) {
                // 查询配送信息
                Delivery delivery = deliveryMapper.selectOne(
                        new QueryWrapper<Delivery>().eq("order_id", order.getOrderId()));

                if (delivery != null) {
                    data.setRiderId(delivery.getRiderId());
                    data.setPhone(delivery.getPhone());
                    data.setEstimatedTime(String.valueOf(delivery.getEstimatedTime()));
                } else {
                    data.setRiderId(null);
                    data.setPhone(null);
                    data.setEstimatedTime(null);
                }
            }
            return data;
        }).collect(Collectors.toList());

        return ApiResponse.success("Get pending order successfully", orderDataList);
    }

    public ApiResponse<List<GetOrderData>> askCompletedOrder(Integer customerId) {
        List<Order> orders = orderMapper.selectList(
                new QueryWrapper<Order>()
                        .eq("customer_id", customerId)
                        .in("status",
                                Arrays.asList(
                                        OrderStatus.COMPLETED,
                                        OrderStatus.CANCELED
                                )
                        ));
        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No completed order found");
        }

        List<GetOrderData> orderDataList = orders.stream().map(GetOrderData::fromOrder).collect(Collectors.toList());
        return ApiResponse.success("Get completed order successfully", orderDataList);
    }
}
