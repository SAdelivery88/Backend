package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.CustomerService;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.CusSelectData;
import group1.sa_delivery.dto.CusSelectRequest;
import group1.sa_delivery.dto.GetRestaurantsData;
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
        return ResponseUtil.success("SelectRestaurant results retrieved successfully", results);
    }
    @GetMapping("/askRestaurant")
    public ResponseEntity<ApiResponse<List<GetRestaurantsData>>> GetRestaurant(){
        List<GetRestaurantsData> results = customerService.getRestaurants();
        return ResponseUtil.success("GetRestaurant results retrieved successfully", results);
    }

}
