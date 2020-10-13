package org.iartisan.admin.template.service.bpm.entity;

/**
 * <p>
 * 离职实体
 *
 * @author King
 * @since 2018/7/11
 */
public class LeaveEntity {

    private String processId;

    private String beginTime;

    private String endTime;

    private String days;

    private String reason;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
