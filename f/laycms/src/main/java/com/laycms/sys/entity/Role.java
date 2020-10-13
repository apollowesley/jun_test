/**
 * 
 */
package com.laycms.sys.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.laycms.base.entity.BaseEntity;


/**
 * @author <p>Innate Solitary 于 2012-5-16 下午6:29:15</p>
 *
 */

@Entity
@Table(name="role")
public class Role extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3152223637499017325L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String roleName;
	private String label;
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
    	return roleName;
    }
	public void setRoleName(String roleName) {
    	this.roleName = roleName;
    }
	public String getLabel() {
    	return label;
    }
	public void setLabel(String label) {
    	this.label = label;
    }
	
	
}
