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
 * <系统配置项实体类>
 * 
 * @author 陆小凤
 * @version [版本号, 2015年7月13日]
 */
@Data
@Entity
@Table(name = "system_config")
public class SystemConfig implements Serializable
{
    private static final long serialVersionUID = 24953091041657L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONFIG_ID")
    private Long configId;
    
    @Column(name = "CONFIG_NAME")
    private String configName;
    
    @Column(name = "CONFIG_DESC")
    private String configDesc;
    
    @Column(name = "CONFIG_VALUE")
    private String configValue;
    
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
