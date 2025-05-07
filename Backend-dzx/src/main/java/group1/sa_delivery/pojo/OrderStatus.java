package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("pending"),//待支付？？？
    CONFIRMED("confirmed"),//已下单？？？
    PREPARING("preparing"),//？？？
    DELIVERING("delivering"),//交付
    COMPLETED("completed"),//完成
    CANCELLED("cancelled");//取消

    @EnumValue  //此字段对应数据库中的值
    private final String dbValue;

    OrderStatus(String dbValue) {
        this.dbValue = dbValue;
    }

}