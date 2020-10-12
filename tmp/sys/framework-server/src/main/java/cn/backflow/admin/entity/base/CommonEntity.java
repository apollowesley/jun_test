package cn.backflow.admin.entity.base;

import java.util.Date;

public abstract class CommonEntity extends BaseEntity {

    protected Date created;

    protected Date updated;

    public Date getCreated() {
        return created;
    }

    public CommonEntity setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getUpdated() {
        return updated;
    }

    public CommonEntity setUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    /*
    public CommonEntity setCreatedString(String createdString) {
        return setCreated(DateUtil.parseDate(createdString));
    }

    public String getCreatedString() {
        return DateUtil.formatDate(getCreated());
    }

    public CommonEntity setUpdatedString(String updatedString) {
        return setUpdated(DateUtil.parseDate(updatedString));
    }

    public String getUpdatedString() {
        return DateUtil.formatDate(getUpdated());
    }
    */
}