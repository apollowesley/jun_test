package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2019/03/01
*/
@Data
@TableName(value = "fw_account_authority")
public class AccountAuthority {
    @TableField(value = "account_id")
    private Long accountId;

    @TableField(value = "authority")
    private String authority;

    public static final String COL_ACCOUNT_ID = "account_id";

    public static final String COL_AUTHORITY = "authority";
}
