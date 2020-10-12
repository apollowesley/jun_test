/**
 * 
 * @auther wuwang
 * @createTime 2014-7-8 下午11:47:50
 */
package com.admin.sysmenu;

import com.admin.AdminBase;
import com.hibernate.voDao.CatchException;
import com.hibernate.voDao.CatchExceptionDAO;

/**
 * 
 * 
 * @author peaches
 */
public class Exception extends AdminBase<CatchException, CatchExceptionDAO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setBean()
	 */
	@Override
	public void setBean() {
		bean = new CatchException();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setDao()
	 */
	@Override
	public void setDao() {
		dao = new CatchExceptionDAO();
	}

	public String update() {
		bean.setException(this.getParameter("exception"));

		dao.save(bean);
		return SUCCESS;
	}

}
