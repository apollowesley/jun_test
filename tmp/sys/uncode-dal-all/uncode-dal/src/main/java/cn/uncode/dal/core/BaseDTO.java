package cn.uncode.dal.core;

import java.io.Serializable;
import java.util.Date;


public abstract class BaseDTO implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -1031601349446401649L;
	
	public static final String ID = "id";
	
	public static final String CREATETIME="createtime";
	
	public static final String VERSION = "version";
	
	/** 主键id*/
    protected Long id;
    /** 版本*/
    protected Integer version;
    /** 创建时间*/
    protected Long createtime;
    

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
	
	public void init(Long id){
		setId(id);
		setCreatetime(new Date().getTime());
	}

	
}
