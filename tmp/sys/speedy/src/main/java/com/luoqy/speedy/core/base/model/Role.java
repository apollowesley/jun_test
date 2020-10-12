package com.luoqy.speedy.core.base.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author qingfeng
 * @since 2019-08-25
 */
@TableName("speedy_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 菜单
     */
    private String menuids;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 部门
     */
    private Integer dept;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 创建人
     */
    private String createuser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuids() {
        return menuids;
    }

    public void setMenuids(String menuids) {
        this.menuids = menuids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    @Override
    public String toString() {
        return "Role{" +
        ", id=" + id +
        ", menuids=" + menuids +
        ", name=" + name +
        ", sort=" + sort +
        ", dept=" + dept +
        ", createtime=" + createtime +
        ", createuser=" + createuser +
        "}";
    }
}
