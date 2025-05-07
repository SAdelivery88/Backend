package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.UserService;
import group1.sa_delivery.dto.*;
import group1.sa_delivery.pojo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import group1.sa_delivery.Security.userDetailService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final userDetailService userdetailService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginData>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/updateUserInfo")
    public ResponseEntity<ApiResponse<Void>> updateUserInfo(@Valid @RequestBody UpdateUserInfoRequest request) {
        if (request.getUserName() != null && request.getUserName().isEmpty()) {
            request.setUserName(null);
        }

        if (request.getPassword() != null && request.getPassword().isEmpty()) {
            request.setPassword(null);
        }

        if (request.getAddress() != null && request.getAddress().isEmpty()) {
            request.setAddress(null);
        }

        if (request.getPhone() != null && request.getPhone().isEmpty()) {
            request.setPhone(null);
        }


        ApiResponse<Void> response = userService.updateUserInfo(userdetailService.getCurrentUser(),request);
        return ResponseEntity.ok(response);
    }
}
