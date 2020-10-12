package cn.backflow.admin.entity;

import cn.backflow.admin.entity.base.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;

public class Role extends BaseEntity {

    @NotBlank(message = "角色名不能为空")
    @Length(max = 32)
    private String name;

    private Integer creator;

    @Length(max = 128)
    private String description;

    @Max(127)
    private Integer status = 1;

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }
}