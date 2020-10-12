package com.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.globalClass.GlobalVariable;
import com.hibernate.voDao.Configuration;
import com.hibernate.voDao.ConfigurationDAO;

public class Configure implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 设置全局变量
		/* 声名 */
		ConfigurationDAO configurationDao = new ConfigurationDAO();
		Configuration configuration = new Configuration();
		/* 网站默认模板 */
		configuration = configurationDao.findById("webDefaultTemplate");
		GlobalVariable.setWebsite_template(configuration.getCvalue());
		/* 手机网站默认模板 */
		configuration = configurationDao.findById("mobileDefaultTemplate");
		GlobalVariable.setWebsite_mobile(configuration.getCvalue());
		/* 网站标题 */
		configuration = configurationDao.findById("websiteTitle");
		GlobalVariable.setWebsite_title(configuration.getCvalue());
		/* 网站名称 */
		configuration = configurationDao.findById("websiteName");
		GlobalVariable.setWebsite_name(configuration.getCvalue());

		System.out.println("Init");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Destroved");
	}
}
