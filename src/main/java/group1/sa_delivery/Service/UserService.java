package group1.sa_delivery.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group1.sa_delivery.dao.UserMapper;
import group1.sa_delivery.dto.*;
import group1.sa_delivery.pojo.Role;
import group1.sa_delivery.pojo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
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

    public ApiResponse<LoginData> login(LoginRequest request){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", request.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        if(user == null)
            return ApiResponse.error(400, "Username not found");
        if(!request.getPassword().equals(user.getPassword()))
            return ApiResponse.error(400, "Password incorrect");

        LoginData loginData = new LoginData(user.getUserId(), user.getPhone(), user.getAddress(),
                                            user.getRole().toString());
        return ApiResponse.success("Login successfully", loginData);
    }

    /**
     * Update user information
     *
     * @param currentUser The user to update
     * @param request Request containing the fields to update
     * @return ApiResponse with success or error message
     */
    public ApiResponse<Void> updateUserInfo(User currentUser, UpdateUserInfoRequest request) {
        try {
            // 检查新用户名是否已被占用
            if (request.getUserName() != null && !currentUser.getUsername().equals(request.getUserName())) {
                if (existUsername(request.getUserName())) {
                    return ApiResponse.error(400, "The username already exists");
                }
                currentUser.setUsername(request.getUserName());
            }

            if (request.getPassword() != null) {
                currentUser.setPassword(request.getPassword());
            }

            if (request.getPhone() != null) {
                currentUser.setPhone(request.getPhone());
            }

            if (request.getAddress() != null) {
                currentUser.setAddress(request.getAddress());
            }

            userMapper.updateById(currentUser);

            return ApiResponse.success("Update " + currentUser.getUsername() + "'s Info successfully", null);
        } catch (Exception e) {
            // 捕获异常并返回 code = 400 的错误响应
            return ApiResponse.error(400, "Failed to update user information: " + e.getMessage());
        }
    }
}
