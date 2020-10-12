package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * Created by Mybatis Generator 2019/03/01
 */
@Data
@TableName(value = "fw_company")
public class Company {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @TableField(value = "address")
    private String address;

    @TableField(value = "code")
    private String code;

    @TableField(value = "contact")
    private String contact;

    @TableField(value = "email")
    private String email;

    @TableField(value = "legal")
    private String legal;

    @TableField(value = "name")
    private String name;

    @TableField(value = "simple_name")
    private String simpleName;

    @TableField(value = "telephone")
    private String telephone;

    public static final String COL_CREATE_DATE = "create_date";

    public static final String COL_UPDATE_DATE = "update_date";

    public static final String COL_ADDRESS = "address";

    public static final String COL_CODE = "code";

    public static final String COL_CONTACT = "contact";

    public static final String COL_EMAIL = "email";

    public static final String COL_LEGAL = "legal";

    public static final String COL_NAME = "name";

    public static final String COL_SIMPLE_NAME = "simple_name";

    public static final String COL_TELEPHONE = "telephone";
}
