package luna.com.firefly.entity.business;

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
 * <网络图片存储>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年7月17日]
 */
@Data
@Entity
@Table(name = "BUSINESS_WEB_PICTURE")
public class WebPicture implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "SOURCE")
    private String source;
    
    @Column(name = "VALUE")
    private String value;
    
    
    @Column(name = "THUMBNAIL")
    private String thumbnail;
    /**
     * 创建时间
     */
    private Date createTime;
    
    public WebPicture()
    {
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
