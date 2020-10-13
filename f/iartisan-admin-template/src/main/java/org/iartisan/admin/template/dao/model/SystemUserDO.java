package org.iartisan.admin.template.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * system_user 表模型
 *
 * @author King
 */
@Data
@Alias("systemUser")
@TableName(value = "system_user")
public class SystemUserDO {

    /**
     * 列名: USER_ID
     * 备注: 主键
     */
    @TableId("USER_ID")
    private String userId;

    /**
     * 列名: USER_NAME
     * 备注: 用户名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 列名: USER_PWD
     * 备注: 密码
     */
    @TableField("USER_PWD")
    private String userPwd;

    /**
     * 列名: STATUS
     * 备注: 员工状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 列名: STAFF_ID
     * 备注: 员工id
     */
    @TableField("STAFF_ID")
    private String staffId;

    /**
     * 列名: CREATE_TIME
     * 备注: 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 列名: UPDATE_TIME
     * 备注: 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;
}