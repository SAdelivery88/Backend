package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER("customer"),
    SELLER("seller"),
    RIDER("rider");

    @EnumValue  //此字段对应数据库中的值
    private final String dbValue;

    Role(String dbValue) {
        this.dbValue = dbValue;
    }

}
