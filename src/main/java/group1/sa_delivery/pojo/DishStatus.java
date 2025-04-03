package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum DishStatus {
    AVAILABLE("available"),
    SOLD_OUT("sold_out"),
    UNLIST("unlist");

    @EnumValue  //此字段对应数据库中的值
    private final String dbValue;

    DishStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}
