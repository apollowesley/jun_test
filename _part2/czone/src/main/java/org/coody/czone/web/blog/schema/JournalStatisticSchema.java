package org.coody.czone.web.blog.schema;

import org.coody.framework.core.entity.BaseModel;

@SuppressWarnings("serial")
public class JournalStatisticSchema extends BaseModel{

	/**
	 * 文章数
	 */
	private Integer journalNum=0;
	/**
	 * 标签数
	 */
	private Integer tagNum=0;
	
	/**
	 * 留言数
	 */
	private Integer feedBackNum=0;
	
	/**
	 * 运行天数
	 */
	private Integer createDays=0;
	/**
	 * 今日访问
	 */
	private Integer visit;
	
	
	public Integer getJournalNum() {
		return journalNum;
	}
	public void setJournalNum(Integer journalNum) {
		this.journalNum = journalNum;
	}
	public Integer getTagNum() {
		return tagNum;
	}
	public void setTagNum(Integer tagNum) {
		this.tagNum = tagNum;
	}
	public Integer getFeedBackNum() {
		return feedBackNum;
	}
	public void setFeedBackNum(Integer feedBackNum) {
		this.feedBackNum = feedBackNum;
	}
	public Integer getCreateDays() {
		return createDays;
	}
	public void setCreateDays(Integer createDays) {
		this.createDays = createDays;
	}
	public Integer getVisit() {
		return visit;
	}
	public void setVisit(Integer visit) {
		this.visit = visit;
	}
	
	
}
