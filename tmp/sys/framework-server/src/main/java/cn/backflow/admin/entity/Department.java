package cn.backflow.admin.entity;


import cn.backflow.admin.common.treeable.Treeable;
import cn.backflow.admin.entity.base.CommonEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class Department extends CommonEntity implements Treeable {

    @Length(max = 32)
    @NotBlank(message = "部门名称不能为空")
    private String name;

    private int parent = 0;

    @Length(max = 255)
    private String ancestors;

    @Length(max = 32)
    private String leader;

    private String leaderName;

    private int level = 1;

    private int priority = 0;

    private List<Treeable> children = new ArrayList<>();

    @Override
    public List<Treeable> getChildren() {
        return children;
    }

    @Override
    public Treenode asTreenode() {
        Treenode node = new Treenode();
        node.id = this.id;
        node.pid = this.parent;
        node.text = this.name;
        node.type = this.parent == 0 ? "#" : "dept";
        return node;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getLeader() {
        return this.leader;
    }

    public void setLeader(String value) {
        this.leader = value;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
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

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer value) {
        this.level = value;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer value) {
        this.priority = value;
    }
}