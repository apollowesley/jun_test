package org.apache.center.common;

import org.apache.playframework.generator.CodeGenerate;
import org.apache.playframework.generator.ConfigGenerator;

public class Generate {

	public static void main(String[] args) {
		ConfigGenerator cg = new ConfigGenerator();
		// 配置 MySQL 连接
		cg.setDbDriverName("com.mysql.jdbc.Driver");
		cg.setDbUser("root");
		cg.setDbPassword("123456");
		cg.setDbUrl("jdbc:mysql://127.0.0.1:3306/center?useUnicode=true");

		// 配置包名
		cg.setEntityPackage("org.apache.center.model");
		cg.setMapperPackage("org.apache.center.dao");
		cg.setServicePackage("org.apache.center.service");
		cg.setServiceName("%sService");
		cg.setXmlPackage("org.apache.center.dao.mapper");
		cg.setResultMap(true);
		cg.setServiceImplPackage("org.apache.center.service.impl");
		cg.setControllerPackage("org.apache.center.controller");
		/*cg.setDtoPackage("org.apache.center.domain");*/
		cg.setJspGenerator(true); // 设置生成JSP
		cg.setDubboRegistryId("centerRegistry"); //设置使用 Dubbo 服务
		// 配置保存路径
		cg.setSaveDir("D:/src");

		/*cg.setTableNames(new String[] { "bank" ,"city_info"});*/
		// 其他参数请根据上面的参数说明自行配置，当所有配置完善后，运行AutoGenerator.run()方法生成Code
		// 生成代码
		new CodeGenerate(cg).execute();
	}

}
