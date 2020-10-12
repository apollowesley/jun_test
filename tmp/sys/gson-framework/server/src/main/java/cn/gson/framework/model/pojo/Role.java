package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * Created by Mybatis Generator 2019/03/01
 */
@Data
@TableName(value = "fw_role")
public class Role {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @TableField(value = "name")
    private String name;

    @TableField(value = "caption")
    private String caption;

    @TableField(value = "rank")
    private Integer rank;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "company_id")
    private Long companyId;

    public static final String COL_CREATE_DATE = "create_date";

    public static final String COL_UPDATE_DATE = "update_date";

    public static final String COL_NAME = "name";

    public static final String COL_CAPTION = "caption";

    public static final String COL_RANK = "rank";

    public static final String COL_STATUS = "status";

    public static final String COL_COMPANY_ID = "company_id";
}
