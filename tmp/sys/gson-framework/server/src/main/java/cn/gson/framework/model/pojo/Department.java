package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mybatis Generator 2019/03/01
 */
@Data
@TableName(value = "fw_department")
public class Department {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @TableField(value = "address")
    private String address;

    @TableField(value = "name")
    private String name;

    @TableField(value = "company_id")
    private Long companyId;

    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(exist = false)
    private List<Department> children = new ArrayList<>();

    public static final String COL_CREATE_DATE = "create_date";

    public static final String COL_UPDATE_DATE = "update_date";

    public static final String COL_ADDRESS = "address";

    public static final String COL_NAME = "name";

    public static final String COL_COMPANY_ID = "company_id";

    public static final String COL_PARENT_ID = "parent_id";
}
