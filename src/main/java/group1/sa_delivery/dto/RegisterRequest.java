package group1.sa_delivery.dto;

import group1.sa_delivery.pojo.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;
    @NotBlank
    private String role;
}
