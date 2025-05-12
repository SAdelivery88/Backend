package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.RiderService;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.RiderOrderRequest;
import group1.sa_delivery.dto.RiderOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    /**
     * rider change the status of order
     *
     * @param request containing order_id
     * */
    @PostMapping("/acceptOrderRider")
    public ResponseEntity<ApiResponse<Void>> acceptOrderRider(
            @Valid @RequestBody RiderOrderRequest request){
        return ResponseEntity.ok(riderService.acceptOrderRider(request));
    }

    @PostMapping("/delivered")
    public ResponseEntity<ApiResponse<Void>> deliveredOrderRider(
            @Valid @RequestBody RiderOrderRequest request){
        return ResponseEntity.ok(riderService.deliveredOrderRider(request));
    }

    /**
     * 骑手查看所有订单
     * @return 包含订单数据的响应
     */
    @GetMapping("/askOrderRider")
    public ResponseEntity<ApiResponse<List<RiderOrderResponse>>> askOrderRider() {
        return ResponseEntity.ok(riderService.askOrderRider());
    }

    /**
     * 骑手查看自己的订单
     * @param riderId 骑手ID
     * @return 包含订单数据的响应
     */
    @GetMapping("/askMyOrderRider")
    public ResponseEntity<ApiResponse<List<RiderOrderResponse>>> askMyOrderRider(@RequestParam("rider_id") int riderId) {
        return ResponseEntity.ok(riderService.askMyOrderRider(riderId));
    }
}
