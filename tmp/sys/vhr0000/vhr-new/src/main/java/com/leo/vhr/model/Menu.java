package com.leo.vhr.model;

import java.util.List;

public class Menu
{
    private Integer id;

    private String url;

    private String path;

    private String component;

    private String name;

    public String getIconCls()
    {
        return iconCls;
    }

    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }

    private String iconCls;

    private Integer parentid;

    private Boolean enabled;

    private Meta meta;

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    private List<Role> roles;

    public Meta getMeta()
    {
        return meta;
    }

    public void setMeta(Meta meta)
    {
        this.meta = meta;
    }

    public List<Menu> getChildren()
    {
        return children;
    }

    public void setChildren(List<Menu> children)
    {
        this.children = children;
    }

    private List<Menu> children;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url == null ? null : url.trim();
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path == null ? null : path.trim();
    }

    public String getComponent()
    {
        return component;
    }

    public void setComponent(String component)
    {
        this.component = component == null ? null : component.trim();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name == null ? null : name.trim();
    }


    public Integer getParentid()
    {
        return parentid;
    }

    public void setParentid(Integer parentid)
    {
        this.parentid = parentid;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }
}