package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    @NotNull(message = "用户id不能为空")
    private Integer userId; //用户id

    @NotBlank(message = "用户名不能为空")
    private String username; //用户名

    @NotBlank(message = "密码不能为空")
    private String password; //密码

    @NotBlank(message = "手机号不能为空")
    private String phone; //手机号

    @NotBlank(message = "地址不能为空")
    private String address; //地址

    @NotNull(message = "用户类型不能为空")
    private Role role; //用户类型
}
