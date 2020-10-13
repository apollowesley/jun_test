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
 * <系统字典表>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年7月17日]
 */
@Data
@Entity
@Table(name = "SYSTEM_DIC")
public class SystemDic implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -2339751794840917306L;
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "VALUE")
    private String value;
    
    @Column(name = "DESCRIPTION")
    private String desc;
    
    @Column(name = "SORT_NUM")
    private Integer sortNum;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 状态更改时间
     */
    private Date updateTime;
    
    public SystemDic()
    {
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
