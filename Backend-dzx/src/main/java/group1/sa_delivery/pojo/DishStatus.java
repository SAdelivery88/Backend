package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum DishStatus {
    AVAILABLE("available"),//在售
    SOLD_OUT("sold_out"),//售空
    UNLIST("unlist");//下架

    @EnumValue  //此字段对应数据库中的值
    private final String dbValue;

    DishStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}
