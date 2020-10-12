package com.foo.common.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/*
 * @Entity declares the class as an entity (i.e. a persistent POJO class), @Id declares the identifier property of this entity. 
 */

@Entity
@Table(name = "foo")
public class Foo {
	@Id
	@javax.persistence.GeneratedValue(generator = "system-uuid")
	@org.hibernate.annotations.GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Expose
	private String id;
	@Expose
	private String parentId;
	@Expose(serialize = false)
	private String text;
	@Expose(serialize = false)
	private String href;
	@Expose(serialize = false)
	private boolean leaf;
	@Expose(serialize = false)
	private String cls;
	@Expose
	private Integer yearFlag;// Should not be int for OGNL
	@Expose
	private Integer monthFlag;

	/*
	 * This list refers to itself, it's not a one to many relationship.So create
	 * a sub-class named: AttachTree
	 */
	// private transient List<AttachTree> children;

	public Integer getYearFlag() {
		return yearFlag;
	}

	public void setYearFlag(Integer yearFlag) {
		this.yearFlag = yearFlag;
	}

	public Integer getMonthFlag() {
		return monthFlag;
	}

	public void setMonthFlag(Integer monthFlag) {
		this.monthFlag = monthFlag;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentId() {
		return parentId;
	}

}
