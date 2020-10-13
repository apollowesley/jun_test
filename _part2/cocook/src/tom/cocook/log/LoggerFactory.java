package tom.cocook.log;

import tom.cocook.core.DefaultLoggerFactory;

public abstract class LoggerFactory {
	
	private static DefaultLoggerFactory defaultLoggerFactory = new DefaultLoggerFactory();
	
	public static Logger getLog(Class<?> clazz){
		return defaultLoggerFactory.getLogger(clazz);
	}

	/**
	 * 
	 * @param name
	 *            the name of the Logger to return
	 * @return a Logger instance
	 */
	public abstract Logger getLogger(String name);

	public abstract Logger getLogger(Class<?> clazz);

}
