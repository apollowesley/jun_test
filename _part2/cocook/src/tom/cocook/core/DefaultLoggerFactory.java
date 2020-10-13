package tom.cocook.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import tom.cocook.config.Constants;
import tom.cocook.log.Logger;
import tom.cocook.log.LoggerFactory;

public class DefaultLoggerFactory extends LoggerFactory {
	
	Map<String, Logger> loggerMap = new HashMap<String, Logger>();
	
	public DefaultLoggerFactory() {
	}
	
	@Override
	public Logger getLogger(String name) {
		try{
			Class.forName("org.slf4j.Logger");
		}catch(Exception e){
			return new DefaultLogger(name);
		}
		if(loggerMap.containsKey(name)) {
			return loggerMap.get(name);
		}
		
		Logger logger = new Slf4jLogger(name);
		synchronized (loggerMap) {
			loggerMap.put(name, logger);
		}
		return logger;
	}

	@Override
	public Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	public static class DefaultLogConfigure implements tom.cocook.log.LogConfigure {
		
		@Override
		public void configure() {
			
            /*log4j 配置*/
            try{
            	Class<?> clazz = Class.forName("org.apache.log4j.PropertyConfigurator");
            	Properties properties = configureFile();
    			
                if (properties == null)  properties = defaultProperties();
            	clazz.getMethod("configure", Properties.class).invoke(null, properties);
            	//org.apache.log4j.PropertyConfigurator.configure(properties);
            	//DOMConfigurator.configureAndWatch(".xml");
            }catch(Exception e){
            	e.printStackTrace();
            }
		}
		
		private Properties configureFile(){
			Properties properties = new Properties();
			InputStream in = null;
			try {
				File file = new File(Constants.getWebRoot(), Constants.getLog4jConfigLocation());
				in = new FileInputStream(file);
				properties.load(in);
			} catch (Exception e) {
				System.out.println("init default logConfig...");
				return null;
			}finally{
				if(in!=null)
					try {in.close();}
					catch (IOException e) {	}
			}
			return properties;
		}
		
		protected Properties defaultProperties() {
            Properties properties = new Properties();

            properties.put("log4j.rootLogger", "INFO, file, stdout");
            properties.put("log4j.appender.file.File", "../logs/cocook.log"); //上一级的logs目录,默认tomcat/logs目录

            properties.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
            properties.put("log4j.appender.stdout.Target", "System.out");
            properties.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
            properties.put("log4j.appender.stdout.layout.ConversionPattern", "%d{ABSOLUTE} %p [%c] - %m%n"); /*console输出格式*/
            
            properties.put("log4j.appender.file", "org.apache.log4j.DailyRollingFileAppender");
            properties.put("log4j.appender.file.DatePattern","'.'yyyy-MM-dd"); /*每天一次*/
            properties.put("log4j.appender.file.Append", "true");
            properties.put("log4j.appender.file.Threshold", "INFO");
            properties.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
            properties.put("log4j.appender.file.layout.ConversionPattern", "%d{ABSOLUTE} %5p [%c{1}] - %m%n");  /*logfile输出格式*/

            return properties;
        }
	}
	public static void main(String[] args) {
		DefaultLogConfigure d = new DefaultLogConfigure();
		d.configure();
		
		Logger logger = LoggerFactory.getLog(DefaultLoggerFactory.class);
		logger.info("sfsafsaf");
	}
	
	
	

}
