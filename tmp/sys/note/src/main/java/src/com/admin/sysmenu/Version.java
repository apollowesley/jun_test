/**
 * 
 * @auther wuwang
 * @createTime 2014-7-8 下午11:47:50
 */
package com.admin.sysmenu;

import com.admin.AdminBase;
import com.hibernate.voDao.VersionDAO;

/**
 * 
 * 
 * @author peaches
 */
public class Version extends AdminBase<com.hibernate.voDao.Version, VersionDAO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setBean()
	 */
	@Override
	public void setBean() {
		bean = new com.hibernate.voDao.Version();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setDao()
	 */
	@Override
	public void setDao() {
		dao = new VersionDAO();
	}

	public String add() {
		// 自动生成一个测试用户
		bean.setVersion("new");
		dao.save(bean);
		return SUCCESS;
	}

	public String update() {
		bean.setId(Integer.valueOf(this.getParameter("id")));
		bean.setVersion(this.getParameter("version"));
		bean.setContent(this.getParameter("content"));
		bean.setDownloadurl1(this.getParameter("downloadurl1"));
		bean.setDownloadurl2(this.getParameter("downloadurl2"));
		bean.setDownloadurl3(this.getParameter("downloadurl3"));
		bean.setDownloadurl4(this.getParameter("downloadurl4"));
		bean.setDownloadurl5(this.getParameter("downloadurl5"));
		bean.setDownloadurl6(this.getParameter("downloadurl6"));
		bean.setDownloadurl7(this.getParameter("downloadurl7"));
		dao.save(bean);
		return SUCCESS;
	}

}
