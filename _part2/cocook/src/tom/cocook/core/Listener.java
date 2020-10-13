package tom.cocook.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public abstract class Listener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		contextInit(arg0);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	protected abstract void contextInit(ServletContextEvent arg0);
}
