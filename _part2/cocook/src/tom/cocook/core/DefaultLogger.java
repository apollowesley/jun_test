package tom.cocook.core;

import java.util.logging.Level;

import tom.cocook.log.Logger;

public class DefaultLogger implements Logger{
	
	private java.util.logging.Logger log;
	private String clazzName;
	
	public DefaultLogger(String name) {
		log = java.util.logging.Logger.getLogger(name);
		clazzName = name;
	}
	

	@Override
	public void debug(String msg) {
		log.logp(Level.FINE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
	}

	@Override
	public void debug(String format, Object arg) {
		
	}

	@Override
	public void debug(String msg, Throwable t) {
		log.logp(Level.FINE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
		
	}

	@Override
	public void info(String msg) {
		log.logp(Level.INFO, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
	}

	@Override
	public void info(String format, Object arg) {
	}

	@Override
	public void info(String msg, Throwable t) {
		log.logp(Level.INFO, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg, t);
	}

	@Override
	public void error(String msg) {
		log.logp(Level.SEVERE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
	}

	@Override
	public void error(String msg, Throwable t) {
		log.logp(Level.SEVERE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
	}

	@Override
	public void error(String format, Object arg) {
		
	}
	
	public boolean isDebugEnabled() {
		return log.isLoggable(Level.FINE);
	}
	
	public boolean isInfoEnabled() {
		return log.isLoggable(Level.INFO);
	}
	
	public boolean isWarnEnabled() {
		return log.isLoggable(Level.WARNING);
	}
	
	public boolean isErrorEnabled() {
		return log.isLoggable(Level.SEVERE);
	}
	
	public boolean isFatalEnabled() {
		return log.isLoggable(Level.SEVERE);
	}

}
