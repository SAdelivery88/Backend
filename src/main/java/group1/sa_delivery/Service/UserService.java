package group1.sa_delivery.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group1.sa_delivery.dao.UserMapper;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.RegisterRequest;
import group1.sa_delivery.pojo.Role;
import group1.sa_delivery.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean existUsername(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    public ApiResponse<Void> register(RegisterRequest request){
        if(existUsername(request.getUsername()))
            return ApiResponse.error(400, "Username already exists");
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        userMapper.insert(user);
        return ApiResponse.success( "Register successfully", null);
    }
}
