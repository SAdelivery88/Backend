package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.CustomerService;
import group1.sa_delivery.dto.*;
import group1.sa_delivery.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/select")
    public ResponseEntity<ApiResponse<List<CusSelectData>>> CusSelectStore(
            @Valid @RequestBody CusSelectRequest request) {
        List<CusSelectData> results = customerService.cusSelectStore(request);
        return ResponseUtil.success("用户选择商家成功", results);
    }
    @GetMapping("/askRestaurant")
    public ResponseEntity<ApiResponse<List<GetRestaurantsData>>> GetRestaurant(){
        List<GetRestaurantsData> results = customerService.getRestaurants();
        return ResponseUtil.success("用户查看商家列表成功", results);
    }
    @PostMapping("/addToCart")
    public ResponseEntity<ApiResponse<Void>> addToCart(@Valid @RequestBody AddCartRequest request) {
        return ResponseEntity.ok(customerService.addToCart(request));
    }
    @PostMapping("/commit")
    public ResponseEntity<ApiResponse<CommitData>> commit(@Valid @RequestBody CommitRequest request){
        return ResponseEntity.ok(customerService.commit(request));
    }
    @DeleteMapping("/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(
            @RequestParam(name = "order_id") Integer orderId){
        return ResponseEntity.ok(customerService.cancel(orderId));
    }
    @PostMapping("/pay")
    public ResponseEntity<ApiResponse<Void>> pay(@Valid @RequestBody PayRequest request){
        return ResponseEntity.ok(customerService.pay(request));
    }
    @GetMapping("/askPendingOrder")
    public ResponseEntity<ApiResponse<List<GetPendingOrderData>>> askPendingOrder(
            @RequestParam(name = "customer_id") Integer customerId) {
        return ResponseEntity.ok(customerService.askPendingOrder(customerId));
    }
    @GetMapping("/askCompletedOrder")
    public ResponseEntity<ApiResponse<List<GetOrderData>>> askCompletedOrder(
            @RequestParam(name = "customer_id") Integer customerId) {
        return ResponseEntity.ok(customerService.askCompletedOrder(customerId));
    }
}
