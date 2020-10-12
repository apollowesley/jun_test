/**
 * 
 * @auther wuwang
 * @createTime 2014-7-8 下午11:47:50
 */
package com.admin.sysmenu;

import com.admin.AdminBase;
import com.globalClass.GlobalVariable;
import com.hibernate.voDao.Configuration;
import com.hibernate.voDao.ConfigurationDAO;
import com.hibernate.voDao.TemplateDAO;

/**
 * 
 * 
 * @author peaches
 */
public class Template extends AdminBase<com.hibernate.voDao.Template, TemplateDAO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setBean()
	 */
	@Override
	public void setBean() {
		bean = new com.hibernate.voDao.Template();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setDao()
	 */
	@Override
	public void setDao() {
		dao = new TemplateDAO();

	}

	/**
	 * 设置网站默认模板
	 * 
	 * @return
	 * @createTime 2014-7-15 下午11:53:59
	 */
	public String setDefault() {
		String id = this.getParameter("id");
		bean = dao.findById(Integer.valueOf(id));
		String dir = bean.getTdirectory();
		ConfigurationDAO configurationDao = new ConfigurationDAO();
		Configuration configuration = new Configuration();
		if (dir.startsWith("/mobile/")) {
			configuration = configurationDao.findById("mobileDefaultTemplate");
			GlobalVariable.setWebsite_mobile(dir);// 设置即时生效
		} else {
			configuration = configurationDao.findById("webDefaultTemplate");
			GlobalVariable.setWebsite_template(dir);// 设置即时生效
		}
		configuration.setCvalue(dir);
		configurationDao.save(configuration);// 保存
		return SUCCESS;
	}
}
