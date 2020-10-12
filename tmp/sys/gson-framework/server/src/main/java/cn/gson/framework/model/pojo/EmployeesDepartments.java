package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2019/03/01
*/
@Data
@TableName(value = "fw_employees_departments")
public class EmployeesDepartments {
     @TableId(value = "employees_id", type = IdType.INPUT)
    private Long employeesId;

     @TableId(value = "department_id", type = IdType.INPUT)
    private Long departmentId;
}
