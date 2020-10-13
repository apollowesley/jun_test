package org.iartisan.admin.template.dao.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

/**
 * system_log 表模型
 *
 * @author King
 */
@Alias("systemLog")
@TableName(value = "system_log")
public class SystemLogDO {

    /**
     * 列名: ID
     * 备注: 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 列名: USER_ID
     * 备注: 用户ID
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 列名: USER_NAME
     * 备注: 用户名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 列名: METHOD
     * 备注: 操作方法
     */
    @TableField("METHOD")
    private String method;

    /**
     * 列名: METHOD_DESC
     * 备注: 操作方法说明
     */
    @TableField("METHOD_DESC")
    private String methodDesc;

    /**
     * 列名: IP
     * 备注: 操作IP
     */
    @TableField("IP")
    private String ip;

    /**
     * 列名: START_TIME
     * 备注: 发起时间
     */
    @TableField("START_TIME")
    private Date startTime;

    /**
     * 列名: END_TIME
     * 备注: 结束时间
     */
    @TableField("END_TIME")
    private Date endTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}