package cn.backflow.admin.entity;

import cn.backflow.admin.entity.base.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class UserResource extends BaseEntity {

    public static final String TABLE_ALIAS = "用户资源";

    private java.lang.Integer userId;

    @NotNull
    private java.lang.Integer resourceId;

    @NotBlank
    @Length(max = 32)
    private java.lang.String resourceType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}