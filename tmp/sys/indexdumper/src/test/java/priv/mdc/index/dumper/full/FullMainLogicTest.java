package priv.mdc.index.dumper.full;

import java.util.List;

import priv.mdc.index.dumper.full.DatabaseBean;
import priv.mdc.index.dumper.full.FullMainLogic;
import junit.framework.TestCase;

public class FullMainLogicTest extends TestCase {

	public void testLoadDbNodes(){
		List<DatabaseBean> databaseBeanList = FullMainLogic.loadDbNodes();
		System.out.println(databaseBeanList);
	}
	
}
