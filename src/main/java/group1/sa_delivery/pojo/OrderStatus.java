package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    PREPARING("preparing"),
    DELIVERING("delivering"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    @EnumValue  //此字段对应数据库中的值
    private final String dbValue;

    OrderStatus(String dbValue) {
        this.dbValue = dbValue;
    }

}