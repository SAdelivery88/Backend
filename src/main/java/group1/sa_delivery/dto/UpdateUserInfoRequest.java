package group1.sa_delivery.dto;

import lombok.Data;

@Data
public class UpdateUserInfoRequest {

    private String userName;
    private String password;
    private String address;
    private String phone;
}
