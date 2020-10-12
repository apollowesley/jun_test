package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2019/03/01
*/
@Data
@TableName(value = "fw_account_roles")
public class AccountRoles {
     @TableId(value = "account_id", type = IdType.INPUT)
    private Long accountId;

     @TableId(value = "role_id", type = IdType.INPUT)
    private Long roleId;
}
