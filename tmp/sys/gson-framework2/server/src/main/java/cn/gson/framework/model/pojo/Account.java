package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * Created by Mybatis Generator 2019/03/01
 */
@Data
@TableName(value = "fw_account")
public class Account {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @TableField(value = "account")
    private String account;

    @JsonIgnore
    @TableField(value = "password")
    private String password;

    @TableField(value = "status")
    private String status;

    @TableField(value = "telephone")
    private String telephone;

    @TableField(value = "employees_id")
    private Long employeesId;

    public static final String COL_CREATE_DATE = "create_date";

    public static final String COL_UPDATE_DATE = "update_date";

    public static final String COL_ACCOUNT = "account";

    public static final String COL_PASSWORD = "password";

    public static final String COL_STATUS = "status";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_EMPLOYEES_ID = "employees_id";
}
