package tom.cocook.core;


import tom.cocook.log.Logger;

public class Slf4jLogger implements Logger{
	
	private org.slf4j.Logger logger;
	
	public Slf4jLogger(String name) {
		logger = org.slf4j.LoggerFactory.getLogger(name);
	}
	
	public Slf4jLogger(Logger logger) {
	}

	@Override
	public void debug(String msg) {
		logger.debug(msg);
	}

	@Override
	public void debug(String format, Object arg) {
		logger.debug(format, arg);
	}

	@Override
	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	@Override
	public void info(String msg) {
		logger.info(msg);
	}

	@Override
	public void info(String format, Object arg) {
		logger.info(format, arg);
	}

	@Override
	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	@Override
	public void error(String msg) {
		logger.error(msg);
	}

	@Override
	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	@Override
	public void error(String format, Object arg) {
		logger.error(format, arg);
	}

	
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}
	
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}
	
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}
	
	public boolean isFatalEnabled() {
		return logger.isTraceEnabled();
	}
	

}
