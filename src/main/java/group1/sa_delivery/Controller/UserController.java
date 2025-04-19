package group1.sa_delivery.Controller;

import group1.sa_delivery.Service.UserService;
import group1.sa_delivery.dto.ApiResponse;
import group1.sa_delivery.dto.RegisterRequest;
import group1.sa_delivery.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

}
