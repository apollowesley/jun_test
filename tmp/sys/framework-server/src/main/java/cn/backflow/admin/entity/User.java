package cn.backflow.admin.entity;

import cn.backflow.admin.entity.base.CommonEntity;
import cn.backflow.lib.util.StringUtil;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User extends CommonEntity {

    public User() {
    }

    // 计算获取资料完整度
    public int getProfileCompletion() {
        int value = 0;
        if (StringUtil.isNotBlank(email)) value += 20;
        if (StringUtil.isNotBlank(phone)) value += 20;
        if (StringUtil.isNotBlank(avatar)) value += 20;
        if (birthday != null) value += 20;
        return value;
    }

    @Email
    @Length(max = 128)
    private String email;

    @Length(max = 13)
    private String phone;

    @Length(max = 32)
    @NotBlank(message = "用户名不能为空")
    private String name;

    @Length(max = 32)
    private String pass;

    private String avatar;

    private int roleId; // 默认普通用户, id为0

    private String roleName;

    private int gender = 0;

    private Integer departmentId;

    private String departmentName;

    private Date birthday;

    private Integer status = 1;  // 状态 1-正常，0-禁用

    @DateTimeFormat
    private Date visited;

    public boolean isAdmin() {
        return roleId == -1;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setPass(String value) {
        this.pass = value;
    }

    public String getPass() {
        return this.pass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String value) {
        this.phone = value;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setBirthday(Date value) {
        this.birthday = value;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setVisited(Date value) {
        this.visited = value;
    }

    public Date getVisited() {
        return this.visited;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}