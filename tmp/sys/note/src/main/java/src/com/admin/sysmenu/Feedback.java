/**
 * 
 * @auther wuwang
 * @createTime 2014-7-8 下午8:37:24
 */
package com.admin.sysmenu;

import com.admin.AdminBase;
import com.hibernate.voDao.Feedbacks;
import com.hibernate.voDao.FeedbacksDAO;

/**
 * 
 * 
 * @author peaches
 */
public class Feedback extends AdminBase<Feedbacks, FeedbacksDAO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setBean()
	 */
	@Override
	public void setBean() {
		bean = new Feedbacks();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setDao()
	 */
	@Override
	public void setDao() {
		dao = new FeedbacksDAO();
	}

	/**
	 * 回复
	 * 
	 * @return
	 * @createTime 2014-7-16 下午9:36:11
	 */
	public String reply() {
		bean = dao.findById(getParameter("id", 0));
		bean.setrcontents(getParameter("reply"));
		bean.setReplystatus(true);
		dao.save(bean);
		return SUCCESS;
	}

	/**
	 * 期望像Struts那样，这个方法也是可以通用的，不过貌似需要用反射做不少事情
	 * 
	 * @return
	 * @createTime 2014-7-16 下午11:19:42
	 */
	public String update() {

		return SUCCESS;
	}
}
