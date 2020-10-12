package org.lanqiao.tjut.test;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;
import org.lanqiao.tjut.db.DBDriverFactory;
import org.lanqiao.tjut.entity.DepartmentEntity;
import org.lanqiao.tjut.entity.EMPSubEntity;
import org.lanqiao.tjut.entity.JWStudentEntity;

public class TestDriver {
	@Test
	public void test01() {
		// 4、进行数据库操作
		// 获取数据操作的sql语句（开发：现在客户端进行sql语句运行验证）
		String strSQL = "select * from Department";

		// List lst = DBDriverFactory.getDBDriverInstance()
		// .doQueryListBean(strSQL,DepartmentBean.class);

		// 获取查询结果list
		List lst = (List) DBDriverFactory.getDBDriverInstance().query(strSQL,
				new BeanListHandler(DepartmentEntity.class));
		// 遍历lst进行查询结果显示
		printList(lst);
	}
	@Test
	public void test02() {
		String strSQL="select *from emp_sub";
		List lst=(List)DBDriverFactory.getDBDriverInstance()
				.query(strSQL, new BeanListHandler(EMPSubEntity.class));	
		printList(lst);}
	
	@Test
	public void test03() {
		// 4、进行数据库操作
		// 获取数据操作的sql语句（开发：现在客户端进行sql语句运行验证）
		//String strSQL = "select count(*) as empcount from employees where department_id = 50";
		String strSQL = "select * from employees where department_id = 50";
		// 获取查询结果list
		BigDecimal empCountDB =  (BigDecimal) DBDriverFactory.getDBDriverInstance()
				.query(strSQL, new ScalarHandler("employee_id"));
		//将bigdecimal类型专为基本数据类型0
		int empCount = empCountDB.intValue();
		// 打印结果
		System.out.println("部分编号为50的员工人数：" + empCount);

	}
	@Test
	public void test04() {
		// 4、进行数据库操作
				// 获取数据操作的sql语句（开发：现在客户端进行sql语句运行验证）
				String strSQL = "select SALARY,EMPID,LAST_NAME from emp_sub";
				// 获取查询结果list
				List lst = (List) DBDriverFactory.getDBDriverInstance()
						.query(strSQL,new MapListHandler());
				// 遍历lst进行查询结果显示
				printList(lst);
	}
	@Test
	public void test05() {
		// 4、进行数据库操作
				// 获取数据操作的sql语句（开发：现在客户端进行sql语句运行验证）
				String strSQL = "select *  from employees where department_id = ? and salary > ?";
				// 获取查询结果list
				List lst = (List) DBDriverFactory.getDBDriverInstance()
						.query(strSQL,new MapListHandler(),new Object[]{50,3000});
				// 遍历lst进行查询结果显示
				printList(lst);
	}
	
	@Test
	public void test06() {
		// 4、进行数据库操作
				// 获取数据操作的sql语句（开发：现在客户端进行sql语句运行验证）
				String strSQL = "INSERT INTO EMP_SUB(LAST_NAME,JOB_ID,SALARY) VALUES(?,?,?)";
				// 获取查询结果list
				int i =  DBDriverFactory.getDBDriverInstance()
						.update(strSQL,new Object[]{"小李22",50,3000});
				// 遍历lst进行查询结果显示
				System.out.println(i>0?"新增成功":"新增失败");
	}
	@Test
	public void test07() {
		// 4、进行数据库操作
				// 获取数据操作的sql语句（开发：现在客户端进行sql语句运行验证）
				String strSQL = "update emp_sub set salary = salary+200 where empid=?";
				// 获取查询结果list
				int i =  DBDriverFactory.getDBDriverInstance()
						.update(strSQL,new Object[]{28});
				// 遍历lst进行查询结果显示
				System.out.println(i>0?"修改成功":"修改失败");
	}
	
	@Test
	public void test08() {
		// 4、进行数据库操作
				// 获取数据操作的sql语句（开发：现在客户端进行sql语句运行验证）
				String strSQL = "INSERT INTO EMP_SUB(LAST_NAME,JOB_ID,SALARY) VALUES(?,?,?)";
				// 获取查询结果list
				int[] arrResult =  DBDriverFactory.getDBDriverInstance()
						.batch(strSQL,new Object[][]{{"小李23",50,3100},{"小李24",50,3200},{"小李25",50,3300}});
				// 遍历lst进行查询结果显示
				for (int i=0;i<arrResult.length;i++) {
					System.out.println("第"+i+"行处理结果："+arrResult[i]);
				}
	}
	
	@Test
	public void test09() {
		// 4、进行数据库操作
				// 获取数据操作的sql语句（开发：现在客户端进行sql语句运行验证）
				//String strSQL = "INSERT INTO EMP_SUB(LAST_NAME,JOB_ID,SALARY) VALUES(?,?,?)";
		String strSQL = "INSERT INTO departments VALUES (?, ?, ?, ?)";
				// 获取查询结果list
				Object[] empSubB =  DBDriverFactory.getDBDriverInstance()
						.insert(strSQL,new ArrayHandler()
								,new Object[]{"210", "Accounting2", "205", "1700"});
				// 遍历lst进行查询结果显示
				System.out.println("结果："+empSubB.toString());
	}
	/**
	 * 使用iterator迭代器遍历list集合
	 */
	public static void printList(List lst) {
		System.out.println("list集合存储的二维表格内容：");

		for (int i = 0; i < lst.size(); i++) {
			// 获取list中的Bean对象
			// EMPSubBean empB = (EMPSubBean) lst.get(i);

			// 打印Bean对象内容信息
			// System.out.println(empB.getLast_name()+"-"+empB.getSalary());;
			System.out.println(lst.get(i).toString());
		}
	}
}
