package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2019/03/01
*/
@Data
@TableName(value = "fw_department_children")
public class DepartmentChildren {
    @TableField(value = "department_id")
    private Long departmentId;

    @TableField(value = "children_id")
    private Long childrenId;

    public static final String COL_DEPARTMENT_ID = "department_id";

    public static final String COL_CHILDREN_ID = "children_id";
}
