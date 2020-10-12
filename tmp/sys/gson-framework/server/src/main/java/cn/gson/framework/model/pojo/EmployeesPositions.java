package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2019/03/01
*/
@Data
@TableName(value = "fw_employees_positions")
public class EmployeesPositions {
     @TableId(value = "employees_id", type = IdType.INPUT)
    private Long employeesId;

     @TableId(value = "position_id", type = IdType.INPUT)
    private Long positionId;
}
