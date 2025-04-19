package group1.sa_delivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group1.sa_delivery.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
