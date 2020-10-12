package tom.cocook.core;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;


import tom.cocook.config.Constants;
import tom.kit.clazz.ReflectUtil;
import tom.kit.io.PropertiesUtil;
import tom.kit.io.Resource;

public class DefaultLogConfigure {
	
	public void config(){
		if(logback()) return;
		log4j();
	}

	boolean log4j() {
		/* log4j 配置 */
		try {
			Class<?> clazz = Class.forName("org.apache.log4j.PropertyConfigurator");
			Resource resource = new Resource(Constants.getLog4jConfigLocation());
			resource.setWebRoot(Constants.getWebRoot());
			Properties properties = PropertiesUtil.loadProperties(resource);

			if (properties == null)		properties = defaultProperties();
			clazz.getMethod("configure", Properties.class).invoke(null, properties);
			// org.apache.log4j.PropertyConfigurator.configure(properties);
			// DOMConfigurator.configureAndWatch(".xml");
			System.out.println("init manual log4j success!");
			return true;
		} catch (Exception e) {
			System.out.println("init manual log4j faild :: "+ e);
			return false;
		}
	}

	boolean logback() {
		Resource resource = new Resource(Constants.getLogbackConfigLocation());
		resource.setWebRoot(Constants.getWebRoot());
		try {
			Class<?> clazz = Class.forName("ch.qos.logback.classic.joran.JoranConfigurator");
			Method setContext = ReflectUtil.findMethod(clazz, "setContext");
			Method doConfigure = ReflectUtil.findMethod(clazz, "doConfigure", InputStream.class);
			Object obj = clazz.newInstance();
			setContext.invoke(obj, Class.forName("ch.qos.logback.classic.LoggerContext").newInstance());
			doConfigure.invoke(obj, resource.getInputStream());
			System.out.println("init manual logback success!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("init manual logback faild ::" +e);
			return false;
		}

	}

	protected Properties defaultProperties() {
		Properties properties = new Properties();

		properties.put("log4j.rootLogger", "INFO, file, stdout");

		properties.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		properties.put("log4j.appender.stdout.Target", "System.out");
		properties.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		properties.put("log4j.appender.stdout.layout.ConversionPattern", "%d{ABSOLUTE} %p [%c] - %m%n"); /* console输出格式 */

		properties.put("log4j.appender.file.File", "../logs/cocook.log"); // 上一级的logs目录,默认tomcat/logs目录
		properties.put("log4j.appender.file", "org.apache.log4j.DailyRollingFileAppender");
		properties.put("log4j.appender.file.DatePattern", "'.'yyyy-MM-dd"); /* 每天一次 */
		properties.put("log4j.appender.file.Append", "true");
		properties.put("log4j.appender.file.Threshold", "INFO");
		properties.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
		properties.put("log4j.appender.file.layout.ConversionPattern", "%d %5p [%c{1}] - %m%n"); /* logfile输出格式 */

		return properties;
	}

}
