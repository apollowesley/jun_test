package luna.com.firefly.entity.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

/**
 * <用户表>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年7月17日]
 */
@Data
@Entity
@Table(name = "system_user")
public class SystemUser implements Serializable
{
    /**
     * 注释
     */
    private static final long serialVersionUID = 1007873213039304224L;
    
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "LOGIN_NAME")
    private String loginName;
    
    @Column(name = "USER_NAME")
    private String name;
    
    // 不持久化到数据库，也不显示在Restful接口的属性.
    @Transient
    @JsonIgnore
    private String plainPassword;
    
    @Column(name = "USER_PWD")
    private String password;
    
    @Column(name = "USER_SALT")
    private String salt;
    
    @Column(name = "USER_ROLES")
    private String roles;
    
    @Column(name = "OPR_USERID")
    private Long createUserId;
    
    @Column(name = "CREATE_TIME")
    private Date registerDate;
    
    @Column(name = "UPDATE_TIME")
    private Date updateDate;
    
    @Column(name = "USER_EMAIL")
    private String email;
    
    @NotBlank
    public String getLoginName()
    {
        return loginName;
    }
    
    @NotBlank
    public String getName()
    {
        return name;
    }
    
    public List<String> getRoleList()
    {
        // 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
        return ImmutableList.copyOf(StringUtils.split(roles, ","));
    }
    
    // 设定JSON序列化时的日期格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getRegisterDate()
    {
        return registerDate;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}