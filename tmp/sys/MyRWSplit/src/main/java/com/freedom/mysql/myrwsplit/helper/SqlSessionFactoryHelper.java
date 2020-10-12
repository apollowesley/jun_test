package com.freedom.mysql.myrwsplit.helper;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @author liuzhq
 * @since 0.1
 * @Email: 837500869@qq.com
 */
public class SqlSessionFactoryHelper {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(SqlSessionFactoryHelper.class);
	private static volatile SqlSessionFactory sessionfactory = null;
	private static String MYBATIS_CONFIG_XML = "mybatis_config.xml";

	private SqlSessionFactoryHelper() {
	}

	static {
		try {
			InputStream inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG_XML);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			sessionfactory = builder.build(inputStream);
			//LOGGER.info("succeed to build global SqlSessionFactory !");
		} catch (Exception e) {
			LOGGER.error(e.toString());
			System.exit(-1);
		} finally {
		}
	}

    //修复为volatile变量
	public static SqlSessionFactory getSqlSessionFactory() {
		return sessionfactory;
	}

}
