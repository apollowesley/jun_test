package org.iartisan.admin.template.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * biz_staff 表模型
 *
 * @author King
 */
@Data
@TableName(value = "biz_staff")
public class BizStaffDO {

    /**
     * 列名: STAFF_ID
     * 备注: 主键
     */
    @TableId("STAFF_ID")
    private String staffId;

    /**
     * 列名: STAFF_NAME
     * 备注: 员工姓名
     */
    @TableField("STAFF_NAME")
    private String staffName;
    /**
     * 列名: STAFF_STATUS
     * 备注: Y:在职 N离职
     */
    @TableField("STAFF_STATUS")
    private String staffStatus;

    /**
     * 列名: STAFF_DEPT
     * 备注: 员工所在部门
     */
    @TableField("STAFF_DEPT")
    private String staffDept;

    /**
     * 列名: STATUS
     * 备注: 数据状态
     */
    @TableField("STATUS")
    private String status;

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