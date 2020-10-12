package cn.coder.jdbc;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class TestOne {
	public static void main(String[] args) {
		// SqlSessionFactory.createSessions();//创建数据源
		// SqlSession session =
		// SqlSessionFactory.getSession("default");//获取某个数据源
		// System.out.println(session.selectOne(Integer.class, "select count(1)
		// from weike"));
		// SqlSessionFactory.destory(); //销毁数据源
		try {
			BeanInfo info = Introspector.getBeanInfo(Weike.class);
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				System.out.println(pd.getName());
				System.out.println(pd.getDisplayName());
				System.out.println(pd.getWriteMethod());
				System.out.println(pd.getReadMethod());
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
