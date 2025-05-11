package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.pojo.Cart_detail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Cart_detailMapper extends BaseMapper<Cart_detail> {

    @Select("SELECT * FROM cart_details WHERE cart_id = #{cartId}")
    List<Cart_detail> selectByCartId(int cartId);


}
