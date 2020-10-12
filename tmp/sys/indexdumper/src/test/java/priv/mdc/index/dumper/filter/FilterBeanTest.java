package priv.mdc.index.dumper.filter;

import java.io.FileNotFoundException;

import priv.mdc.index.dumper.filter.FilterBean;
import priv.mdc.index.dumper.filter.FilterModel;
import junit.framework.TestCase;

public class FilterBeanTest extends TestCase {

	public void testConstruct(){
		try {
			FilterBean bean = FilterModel.queryFilterBean("USERS");
			System.out.print(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
