/**
 * 
 */
package com.laycms.sys.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.laycms.base.entity.BaseEntity;


/**
 * @author <p>Innate Solitary 于 2012-5-16 下午5:45:38</p>
 *
 */

@Entity
@Table(name="menu")
public class Menu extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4629157786332782876L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String menuName;
	private String menuPath;
	private int menuSeq;
	private Integer parentMenuId;
	
	private Date createTime;
	
	@Transient
	private List<Menu> subMenu;
	
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
	public String getMenuName() {
    	return menuName;
    }
	public void setMenuName(String menuName) {
    	this.menuName = menuName;
    }
	public String getMenuPath() {
    	return menuPath;
    }
	public void setMenuPath(String menuPath) {
    	this.menuPath = menuPath;
    }
	public int getMenuSeq() {
    	return menuSeq;
    }
	public void setMenuSeq(int menuSeq) {
    	this.menuSeq = menuSeq;
    }
	public Integer getParentMenuId() {
    	return parentMenuId;
    }
	public void setParentMenuId(Integer parentMenuId) {
    	this.parentMenuId = parentMenuId;
    }
	public List<Menu> getSubMenu() {
    	return subMenu;
    }
	public void setSubMenu(List<Menu> subMenu) {
    	this.subMenu = subMenu;
    }
	
	
	
}
