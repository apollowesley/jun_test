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
 * <角色实体类>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年7月17日]
 */
@Data
@Entity
@Table(name = "system_role")
public class SystemRole implements Serializable
{
    /**
     * 注释
     */
    private static final long serialVersionUID = 3260110878096913809L;
    
    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ROLE_NAME")
    private String name;
    
    @Column(name = "ROLE_DESC")
    private String description;
    
    // 创建人
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}