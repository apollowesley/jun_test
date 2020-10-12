package com.leo.vhr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department implements Serializable
{
    private Integer id;

    private String name;

    private Integer parentId;

    private String dePath;

    private Boolean enabled;

    private Boolean isParent;

    public Department(String name)
    {
        this.name = name;
    }

    public Department()
    {
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Department that = (Department) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    private List<Department> children=new ArrayList<>();

    private Integer result;

    public Integer getResult()
    {
        return result;
    }

    public void setResult(Integer result)
    {
        this.result = result;
    }

    public List<Department> getChildren()
    {
        return children;
    }

    public void setChildren(List<Department> children)
    {
        this.children = children;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public String getDePath()
    {
        return dePath;
    }

    public void setDePath(String dePath)
    {
        this.dePath = dePath;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public Boolean getParent()
    {
        return isParent;
    }

    public void setParent(Boolean parent)
    {
        isParent = parent;
    }
}