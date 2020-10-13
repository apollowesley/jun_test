package luna.com.firefly.entity.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <系统菜单实体类>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年7月17日]
 */
@Data
@Entity
@Table(name = "system_menu")
public class SystemMenu implements Serializable
{
    private static final long serialVersionUID = 12131231231233L;
    
    @Id
    @Column(name = "MENU_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "MENU_NAME")
    private String name;
    
    @Column(name = "MENU_ALIAS")
    private String alias;
    
    @Column(name = "MENU_DESC")
    private String description;
    
    @Column(name = "MENU_LINK")
    private String link;
    
    @Column(name = "MENU_SORTNUM")
    private Integer sort;
    
    @JsonProperty("parentId")
    @Column(name = "MENU_PARENTID")
    private Long parentId;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    @JsonIgnore
    @Transient
    private List<SystemMenu> childMenus;
    
    @JsonIgnore
    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SystemButton> childButton;
    
    @Transient
    private boolean checked;
    
    @Transient
    private String state;
    
    public boolean isChecked()
    {
        return checked;
    }
    
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    
    public List<SystemMenu> getChildMenus()
    {
        return childMenus;
    }
    
    public void setChildMenus(List<SystemMenu> childMenus)
    {
        this.childMenus = childMenus;
    }
    
    public List<SystemButton> getChildButton()
    {
        return childButton;
    }
    
    public void setChildButton(List<SystemButton> childButton)
    {
        this.childButton = childButton;
    }
    
    public String getState()
    {
        if (0 == this.getParentId())
            return "closed";
        return null;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SystemMenu other = (SystemMenu)obj;
        if (alias == null)
        {
            if (other.alias != null)
                return false;
        }
        else if (!alias.equals(other.alias))
            return false;
        if (checked != other.checked)
            return false;
        if (childButton == null)
        {
            if (other.childButton != null)
                return false;
        }
        else if (!childButton.equals(other.childButton))
            return false;
        if (childMenus == null)
        {
            if (other.childMenus != null)
                return false;
        }
        else if (!childMenus.equals(other.childMenus))
            return false;
        if (createTime == null)
        {
            if (other.createTime != null)
                return false;
        }
        else if (!createTime.equals(other.createTime))
            return false;
        if (createUser == null)
        {
            if (other.createUser != null)
                return false;
        }
        else if (!createUser.equals(other.createUser))
            return false;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (link == null)
        {
            if (other.link != null)
                return false;
        }
        else if (!link.equals(other.link))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (parentId == null)
        {
            if (other.parentId != null)
                return false;
        }
        else if (!parentId.equals(other.parentId))
            return false;
        if (sort == null)
        {
            if (other.sort != null)
                return false;
        }
        else if (!sort.equals(other.sort))
            return false;
        if (state == null)
        {
            if (other.state != null)
                return false;
        }
        else if (!state.equals(other.state))
            return false;
        if (updateTime == null)
        {
            if (other.updateTime != null)
                return false;
        }
        else if (!updateTime.equals(other.updateTime))
            return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + (checked ? 1231 : 1237);
        result = prime * result + ((childButton == null) ? 0 : childButton.hashCode());
        result = prime * result + ((childMenus == null) ? 0 : childMenus.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((link == null) ? 0 : link.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + ((sort == null) ? 0 : sort.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
        return result;
    }
    
}