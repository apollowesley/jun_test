package com.site.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Vincent.wang
 *
 */
@Document(collection = "logs")
public class BusiLog implements BaseEntity<String> {

    private static final long serialVersionUID = 1538192110129669137L;

    @Id
    @Field("_id")
    private String id;

    // 日志对应的业务系统标识
    @Field("system_name")
    private String systemName;

    // 日志产生的时间
    @Field("create_time")
    private Date createTime;

    // 日志级别
    @Field("level")
    private String level;

    // 日志信息当的线程名
    @Field("thread_name")
    private String threadName;

    // 日志当中类名称
    @Field("log_name")
    private String logName;

    // 日志的描述
    @Field("message")
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
