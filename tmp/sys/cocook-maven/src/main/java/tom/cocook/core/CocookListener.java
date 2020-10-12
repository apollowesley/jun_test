package tom.cocook.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import tom.cocook.handler.Handler;
import tom.db.jdbc.simple.DBUtil;

/**
 * 继承回收内存和数据池
 * @author tomsun
 */
public abstract class CocookListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		contextInit(arg0);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		DBUtil.closeDataSource();
		clearHandler();
	}
	
	private void clearHandler(){
		for(String key :Handler.handlers.keySet()){
			Handler handler = Handler.handlers.get(key);
			handler.getApps().clear();
		}
		Handler.handlers.clear();
	}

	protected abstract void contextInit(ServletContextEvent arg0);
}
