package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.RiderService;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.RiderOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
