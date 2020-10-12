package tom.test.listener;

import javax.servlet.ServletContextEvent;
import tom.cocook.core.CocookListener;
import tom.db.jdbc.simple.DBUtil;

public class ShareelbListener extends CocookListener{

	@Override
	protected void contextInit(ServletContextEvent arg0) {
		System.out.println("........contextInit ........");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		super.contextDestroyed(arg0);
		DBUtil.closeDataSource();
		System.out.println("........contextDestroyed ........");
	}
	
	

}
