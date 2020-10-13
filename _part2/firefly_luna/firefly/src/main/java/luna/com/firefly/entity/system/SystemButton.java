package luna.com.firefly.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * < 按钮实体类>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年7月17日]
 */
@Data
@Entity
@Table(name = "system_button")
public class SystemButton implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2686424043984131226L;
    
    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUTTON_ID")
    private Long buttonId;
    
    // 按钮代码
    @Column(name = "BUTTON_CODE")
    private String buttonCode;
    
    // 按钮名称
    @Column(name = "BUTTON_NAME")
    private String buttonName;
    
    // 按钮描述
    @Column(name = "BUTTON_DESC")
    private String buttonDesc;
    
    // 创建人
    @Column(name = "CREATE_USER")
    private String createUser;
    
    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    // 更新时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MENU_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    private SystemMenu menu;
    
    @Transient
    private boolean checked;
    
    public boolean isChecked()
    {
        return checked;
    }
    
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
