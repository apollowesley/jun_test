package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2019/03/01
*/
@Data
@TableName(value = "fw_role_authority")
public class RoleAuthority {
    @TableField(value = "role_id")
    private Long roleId;

    @TableField(value = "authority")
    private String authority;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_AUTHORITY = "authority";
}
