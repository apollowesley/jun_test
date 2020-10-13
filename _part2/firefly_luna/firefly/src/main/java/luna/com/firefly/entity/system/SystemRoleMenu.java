package luna.com.firefly.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <角色-菜单关系表>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年7月17日]
 */
@Data
@Entity
@Table(name = "system_role_menu_rel")
public class SystemRoleMenu implements Serializable
{
    private static final long serialVersionUID = -5924532631770170659L;
    
    // 注释
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REL_ID")
    private Long relId;
    
    // 注释
    @Column(name = "MENU_ID")
    private Long menuId;
    
    // 注释
    @Column(name = "ROLE_ID")
    private Long roleId;
    
    // 注释
    @Column(name = "CREATE_USER")
    private String createUser;
    
    // 注释
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    // 注释
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}