package group1.sa_delivery.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group1.sa_delivery.Security.SecurityUser;
import group1.sa_delivery.dao.UserMapper;
import group1.sa_delivery.dto.*;
import group1.sa_delivery.pojo.Role;
import group1.sa_delivery.pojo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean existUsername(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    public ApiResponse<Void> register(RegisterRequest request){
        if(existUsername(request.getUsername()))
            return ApiResponse.error(400, "Username already exists");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        userMapper.insert(user);

        return ApiResponse.success( "Register successfully", null);
    }

    public ApiResponse<LoginData> login(LoginRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            SecurityUser securityUser = (SecurityUser)authentication.getPrincipal();
            User user = securityUser.getUser();

            LoginData loginData = new LoginData(user.getUserId(), user.getPhone(), user.getAddress(),
                    user.getRole().toString());
            return ApiResponse.success("Login successfully", loginData);
        }catch (BadCredentialsException e){
            return ApiResponse.error(400, "Wrong username or password");
        }
    }

    /**
     * Update user information
     *
     * @param currentUser The user to update
     * @param request Request containing the fields to update
     * @return ApiResponse with success or error message
     */
    public ApiResponse<Void> updateUserInfo(User currentUser, UpdateUserInfoRequest request, HttpServletRequest httpServletRequest) {
        try {
            String originalUsername = currentUser.getUsername(); // 保存原用户名

            // 检查新用户名是否已被占用
            if (request.getUserName() != null && !originalUsername.equals(request.getUserName())) {
                if (existUsername(request.getUserName())) {
                    return ApiResponse.error(400, "新用户名已被占用");
                }
                currentUser.setUsername(request.getUserName());
            }

            if (request.getPassword() != null) {
                currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            if (request.getPhone() != null) {
                currentUser.setPhone(request.getPhone());
            }

            if (request.getAddress() != null) {
                currentUser.setAddress(request.getAddress());
            }

            userMapper.updateById(currentUser);

            // 重新查询最新用户信息（避免缓存问题）
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", currentUser.getUserId());
            User updatedUser = userMapper.selectOne(wrapper);
            if (updatedUser == null) {
                return ApiResponse.error(400, "Updated user not found");
            }

            // 更新 session 中的用户信息
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("currentUser", updatedUser);

            // 若修改了用户名，更新Security上下文
            if (!originalUsername.equals(updatedUser.getUsername())) {
                UserDetails newUserDetails = new SecurityUser(updatedUser);
                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    newUserDetails, 
                    updatedUser.getPassword(), 
                    newUserDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }

            return ApiResponse.success("修改UserId为 " + updatedUser.getUserId() + "的信息成功", null);
        } catch (Exception e) {
            // 捕获异常并返回 code = 400 的错误响应
            return ApiResponse.error(400, "更新失败: " + e.getMessage());
        }
    }
}
