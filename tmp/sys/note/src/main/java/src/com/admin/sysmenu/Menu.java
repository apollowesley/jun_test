/**
 * 
 * @auther wuwang
 * @createTime 2014-6-23 下午9:27:17
 */
package com.admin.sysmenu;

import java.util.List;

import com.admin.AdminBase;
import com.hibernate.voDao.admin.Sysmenu;
import com.hibernate.voDao.admin.SysmenuDAO;

/**
 * 
 * 
 * @author peaches
 */
public class Menu extends AdminBase<Sysmenu, SysmenuDAO> {

	/**
	 * 获取横向菜单
	 * 
	 * @return
	 * 
	 * @createTime 2014-6-23 下午11:18:29
	 */
	public String getH() {
		List list = dao.getH();
		return toJson(list);
	}

	/**
	 * 根据横向菜单的点击，得到纵向子菜单
	 * 
	 * @return
	 * @createTime 2014-7-6 下午10:54:11
	 */
	public String getV() {
		List list = dao.getV(this.getParameter("id"));
		return toJson(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setBean()
	 */
	@Override
	public void setBean() {
		bean = new Sysmenu();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setDao()
	 */
	@Override
	public void setDao() {
		dao = new SysmenuDAO();

	}

}
