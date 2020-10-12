package cn.coder.jdbc;

import java.io.Serializable;
import java.util.Date;

import cn.coder.jdbc.annotation.Column;
import cn.coder.jdbc.annotation.Id;
import cn.coder.jdbc.annotation.Table;

 @Table("weike")
public class Weike implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**  */
	@Id(true)
	@Column("id")
	private Integer id;

	/**  */
	@Column("title")
	private String title;

	/**  */
	@Column("author")
	private String author;

	/**  */
	@Column("ctime")
	private Date ctime;

	/**  */
	@Column("wk_img")
	private String wkImg;

	/**  */
	@Column("wk_href")
	private String wkHref;

	/**  */
	@Column("type")
	private String type;


	/**  */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**  */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**  */
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/**  */
	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	/**  */
	public String getWkImg() {
		return wkImg;
	}

	public void setWkImg(String wkImg) {
		this.wkImg = wkImg;
	}

	/**  */
	public String getWkHref() {
		return wkHref;
	}

	public void setWkHref(String wkHref) {
		this.wkHref = wkHref;
	}

	/**  */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}