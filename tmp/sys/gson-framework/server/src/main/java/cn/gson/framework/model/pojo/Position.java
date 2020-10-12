package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * Created by Mybatis Generator 2019/03/01
 */
@Data
@TableName(value = "fw_position")
public class Position {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @TableField(value = "name")
    private String name;

    @TableField(value = "responsibility")
    private String responsibility;

    @TableField(value = "rank")
    private Integer rank;

    @TableField(value = "company_id")
    private Long companyId;

    public static final String COL_CREATE_DATE = "create_date";

    public static final String COL_UPDATE_DATE = "update_date";

    public static final String COL_NAME = "name";

    public static final String COL_RESPONSIBILITY = "responsibility";

    public static final String COL_RANK = "rank";

    public static final String COL_COMPANY_ID = "company_id";
}
