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
 * <日志记录实体类>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年9月14日]
 */
@Data
@Entity
@Table(name = "system_log")
public class SystemLog implements Serializable
{
    private static final long serialVersionUID = 7666414469336601402L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROLOG_ID")
    private Long proLogId;
    
    private String oprType;
    
    private Date oprTime;
    
    private String oprTableName;
    
    private String oprDataId;
    
    private String oprDataOld;
    
    private String oprDataNew;
    
    private Long oprUserId;
    
    private String oprUserName;
    
    private String oprUserIp;
    
    private String oprComment;
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
