package org.iartisan.admin.template.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * biz_dept 表模型
 *
 * @author King
 */
@Data
@ToString
@TableName(value = "biz_dept")
public class BizDeptDO {

    /**
     * 列名: DEPT_ID
     * 备注: 主键
     */
    @TableId("DEPT_ID")
    private String deptId;

    /**
     * 列名: DEPT_NAME
     * 备注: 部门名
     */
    @TableField("DEPT_NAME")
    private String deptName;

    /**
     * 列名: DEPT_LEADER
     * 备注: 部门领导
     */
    @TableField("DEPT_LEADER")
    private String deptLeader;
    /**
     * 列名: DEPT_PATH
     * 备注: 部门树
     */
    @TableField("DEPT_PATH")
    private String deptPath;

    /**
     * 列名: DEPT_PARENT
     * 备注: 上一级部门
     */
    @TableField("DEPT_PARENT")
    private String deptParent;
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