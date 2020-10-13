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
 * <用户 角色关系表>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年7月17日]
 */
@Data
@Entity
@Table(name = "system_user_role_rel")
public class SystemUserRole implements Serializable
{
    private static final long serialVersionUID = -5642256333944208676L;
    
    // 注释
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REL_ID")
    private Long relId;
    
    // 注释
    @Column(name = "USER_ID")
    private Long userId;
    
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
    
    public Long getRoleId()
    {
        return roleId;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}