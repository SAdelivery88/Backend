package group1.sa_delivery.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data               //为所有字段添加get/set方法、toString方法、equals方法、hashCode方法
@NoArgsConstructor  //生成无参构造方法
@AllArgsConstructor //生成全参构造方法
@TableName("deliveries")    //指定表名
public class Delivery {
    /* TableId注解结合数据库中主键AUTO_INCREMENT，实现插入时主键id的自增
    创建Delivery对象时，无需设置该id */
    @TableId(type = IdType.AUTO)
    @NotNull(message = "配送单id不能为空")
    private Integer deliveryId; //配送单id

    /* MyBatisPlus配置已开启自动驼峰转换，
    orderId对应Deliveries表中order_id，riderId对应rider_id...
    NotNull NotBlank注解需结合@Valid注解使用，如果要用的话要加异常处理 */
    @NotNull(message = "订单id不能为空")
    private Integer orderId;    //订单id

    @NotNull(message = "配送员id不能为空")
    private Integer riderId;    //配送员id

    @NotBlank(message = "配送员姓名不能为空")
    private String phone;       //配送员电话

    @NotBlank(message = "配送地址不能为空")
    private String address;     //配送地址

    /* MyBatisPlus自动完成LocalDateTime和datetime的转换 */
    private LocalDateTime estimatedTime;    //预计送达时间
}
