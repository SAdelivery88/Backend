package group1.sa_delivery.Service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group1.sa_delivery.dao.RestaurantMapper;
import group1.sa_delivery.dto.SearchData;
import group1.sa_delivery.dto.SearchRequest;
import group1.sa_delivery.pojo.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {
    private final RestaurantMapper restaurantMapper;

    /**
     * Search restaurants by keyword with optional filters
     *
     * @param key Search keyword for restaurant name or description
     * @param page ,default:page=1,size=10
     * @return Page of SearchData objects matching the search criteria
     */
    public IPage<SearchData> searchRestaurant(String key, @NotNull Integer page) {
        Integer size = 10;
        // 创建分页对象
        Page<Restaurant> pageParam = new Page<>(page, size);
        
        // 构建MyBatis-Plus查询条件
        QueryWrapper<Restaurant> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", key)
                   .or()
                   .like("address", key);
        
        // 执行分页查询（需要确保RestaurantMapper继承BaseMapper）
        Page<Restaurant> restaurantPage = restaurantMapper.selectPage(pageParam, queryWrapper);
        
        // 转换为DTO分页对象
        return restaurantPage.convert(SearchData::convertToSearchData);
    }

    public List<SearchData> searchRestaurantWithFilter(SearchRequest request) {
        IPage<SearchData> results = searchRestaurant(
            request.getKey(),
            request.getPage()
        );
        return results.getRecords(); // 直接获取分页记录
    }
}
