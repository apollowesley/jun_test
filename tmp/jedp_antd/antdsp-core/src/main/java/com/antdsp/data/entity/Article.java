package com.antdsp.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="tb_article")
@Entity
public class Article extends AbstractEntity{
	
	@Column(name="title", length=16 , nullable=false)
	private String title;
	
	@Column(name="sub_title", length=256 , nullable=false)
	private String subTitle;
	
	/**
	 * 文章来源
	 */
	@Column(name="article_from", length=256 , nullable=false)
	private String articleFrom;
	
	@Column(name="content", length=10240 )
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getArticleFrom() {
		return articleFrom;
	}

	public void setArticleFrom(String articleFrom) {
		this.articleFrom = articleFrom;
	}
	
	
}
