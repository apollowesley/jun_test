package cn.backflow.admin.entity;

import cn.backflow.admin.common.treeable.Treeable;
import cn.backflow.admin.entity.base.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class Permission extends BaseEntity implements Treeable {

    @Length(max = 64)
    private String code;

    @Length(max = 64)
    @NotBlank(message = "权限名称不能为空")
    private String name;

    @Length(max = 255)
    private String description;

    private String icon;

    private int parent = 0;

    @Length(max = 255)
    private String ancestors;

    private Integer level = 1;

    private Integer priority = 0;

    private List<Treeable> children = new ArrayList<>();

    public List<Treeable> getChildren() {
        return children;
    }

    @Override
    public Treenode asTreenode() {
        Treenode node = new Treenode();
        node.id = this.id;
        node.pid = this.parent;
        node.text = this.name;
        node.icon = this.icon;
        node.code = this.code;
        node.type = this.parent == 0 ? "#" : "perm";
        return node;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getParent() {
        return this.parent;
    }

    public void setParent(Integer value) {
        this.parent = value;
    }

    public String getAncestors() {
        return this.ancestors;
    }

    public void setAncestors(String value) {
        this.ancestors = value;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer value) {
        this.level = value;
    }
}