package cn.backflow.admin.entity;


import javax.validation.constraints.NotNull;

public class UserDepartment {

    private Integer id;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer departmentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}