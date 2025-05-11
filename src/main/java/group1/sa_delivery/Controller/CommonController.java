package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.CommonService;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.SearchData;
import group1.sa_delivery.dto.SearchRequest;
import group1.sa_delivery.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    /**
     * Search restaurant by key.
     *
     * @param request Search request containing key,default:page=1,size=10
     * @return ResponseEntity with list of matching restaurants
     */
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<SearchData>>> searchRestaurant(
            @Valid @RequestBody SearchRequest request){
        List<SearchData> results = commonService.searchRestaurantWithFilter(request);
        return ResponseUtil.success("Search results retrieved successfully", results);
    }
}
