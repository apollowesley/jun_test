package org.lanqiao.tjut.entity;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;
import org.lanqiao.tjut.db.DBDriverFactory;

public class StuFuction {
	Scanner input = new Scanner(System.in);

	public void s() {
		while (true) {
			System.out.println("*****学生系统登入中*****");
			// 输入学生的code与密码
			StuFuction st = new StuFuction();
			boolean isF = st.studententer();
			if (isF) {
				System.out.println("欢迎登入学生系统！");
				// 调用学生的一些列行为
				st.dosf();

			} else {
				System.out.println("1重新登录    2退出");
				int a = input.nextInt();
				if (a == 2) {
					break;
				}
			}
		}
	}

	public boolean studententer() {
		System.out.println("输入你的学号s_num:");
		String stu_num = input.next();
		System.out.println("输入你的密码s_code:(默认密码为0)");
		String stu_code = input.next();
		String strSql = "select * from JWSTUDENT where s_num=? and s_code=?";
		List lst = (List) DBDriverFactory.getDBDriverInstance().query(strSql,
				new BeanListHandler(JWStudentEntity.class), new Object[] { stu_num, stu_code });
		if (lst.size() > 0) {
			if (stu_code.equals("0")) {
				System.out.println("是否需要更改密码？1更改    2不更改");
				int b = input.nextInt();
				if (b == 1) {
					boolean isF = true;
					while (isF) {
						System.out.println("请输入你要更改的新密码：");
						String str1 = input.next();
						System.out.println("请确认你的新密码：");
						String str2 = input.next();
						if (str1.equals(str2)) {
							String str3 = "UPDATE ZSSTUDENT SET s_num=? WHERE s_code=?";
							int i = DBDriverFactory.getDBDriverInstance().update(str3, new Object[] { str2, stu_num });
							System.out.println(i > 0 ? "密码修改成功" : "密码修改失败");
							isF = false;
						} else {
							System.out.println("两遍密码不相同，请清醒后再重新输入！");
						}
					}
				}
			}
			return true;
		} else {
			return false;
		}

	}

	@Test
	public void dosf() {

		System.out.println("1查询同学信息");
		System.out.println("2添加好友");
		System.out.println("3 显示我的所有好友信息");
		System.out.println("4 删除好友");
		System.out.println("5  修改好友备注");
		System.out.println("0......");
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		switch (a) {

		case 0:
			break;
		case 1:
			chaxun();

			break;
		case 2:
			tianjia();
			break;
			
		case 3:
			
			System.out.println("请输入自己的学号");
			int z= sc.nextInt();
			String strSQL=" select *from  JWFRIEND where f_s_num=?";
			List lst4 = DBDriverFactory.getDBDriverInstance().query(strSQL, new MapListHandler(),new Object[]{z});
			printList(lst4);
			break;
		case 4:
			shanchu();
			break;
		case 5:
			xiugai();
			break;
		}
	}
private void xiugai() {
	while (true) {
	
		System.out.println("请输入你要修改备注好友的学号：");
		int f_num= input.nextInt();
		System.out.println("备注修改为：");
		String str = input.next();
		String strSql="update JWFRIEND set f_beizhu=? where st f_num=?";
		int i=DBDriverFactory.getDBDriverInstance().update(strSql, new Object[]{str,f_num});
		System.out.println(i > 0 ? "备注修改成功" : "备注修改失败");
		
		System.out.println("1继续修改备注、    2返回上一层        3退出登录");
		int b = input.nextInt();
		if (b == 2) {
			dosf() ;
		}else if(b==3){
			break;
		}
	}
		
	}

//删除
	private void shanchu() {
	
		while (true) {
		
			System.out.println("输入你要删除的好友学号");
			int f_id = input.nextInt();
			String strSql1="delete from jwfriend where f_num=?";
			int i =DBDriverFactory.getDBDriverInstance().update(strSql1, new Object[]{f_id});
			System.out.println(i > 0 ? "好友删除成功" : "好友删除失败");
			String strSql2="delete from jwfriend where f_s_num=?";
			int i1 =DBDriverFactory.getDBDriverInstance().update(strSql2, new Object[]{f_id});
			System.out.println(i1 > 0 ? "将自己从对方列表删除成功" : "将自己从对方列表删除失败");
			
			System.out.println("【1】继续删除好友---【2】返回上一层【3】退出登录");
			int b = input.nextInt();
			if (b == 2) {
				 dosf();
			}else if(b==3){
			break;
			}
		}
	}
//添加
	private void tianjia() {
		while(true){
			System.out.println("输入自己的学号");
			int a= input.nextInt();
			System.out.println("输入添加的好友学号)");
			int s = input.nextInt();
			System.out.println("添加备注为：");
			String str1 = input.next();
			String strSql1 = "insert into JWFRIEND(f_num,f_s_num,f_beizhu)values(?,?,?)";
			int i1=DBDriverFactory.getDBDriverInstance().update(strSql1, new Object[]{s,a,str1});
			System.out.println(i1 > 0 ? "添加好友成功" : "添加好友失败");
			String strSql2 = "insert into  JWFRIEND(f_num,f_s_num,f_beizhu) VALUES(?,?,?)";
			int i2 = DBDriverFactory.getDBDriverInstance().update(strSql2, new Object[] { a, s, "好友" });
			System.out.println(i2 > 0 ? "对方添加你成功" : "对方添加你失败");
			
			System.out.println("【1】继续添加好友---【2】返回上一层【3】退出登录");
			int b = input.nextInt();
			if (b == 2) {
				 dosf();
			}else if(b==3){
			break;
			}
		}
		
	}
/**
 * 
 * 查询
 */
	private void chaxun() {

		while (true) {
			System.out.println("1查询所有学生的信息");
			System.out.println("2根据指定编号查询单个学生信息");
			System.out.println("3根据姓名模糊查询学生信息");
			System.out.println("4根据地址模糊查询学生信息");
			System.out.println("5根据年龄段查询学生信息");
			System.out.println("0 退出");
			Scanner sc = new Scanner(System.in);
			int a = sc.nextInt();
			switch (a) {
			case 1:
				String strSQL = "select * from JWStudent";
				List lst = (List) DBDriverFactory.getDBDriverInstance().query(strSQL,
						new BeanListHandler(JWStudentEntity.class));
				printList(lst);
				break;

			case 2:
				System.out.println("请输入你要查询的学生的num为：");
				int d = sc.nextInt();
				String strSQL1 = "SELECT * FROM JWSTUDENT WHERE s_num='" + d + "'";
				List lst1 = DBDriverFactory.getDBDriverInstance().query(strSQL1, new MapListHandler());
				printList(lst1);
				break;

			case 3:
				System.out.println("请输入学生的姓");
				String str = sc.next();
				String strSQL2 = "SELECT * FROM JWSTUDENT WHERE s_name like'" + str + "%'";
				List lst2 = DBDriverFactory.getDBDriverInstance().query(strSQL2, new MapListHandler());
				printList(lst2);
				break;

			case 4:

				System.out.println("请输入学生地址首字？");
				String str1 = sc.next();
				String strSQL3 = "SELECT * FROM JWSTUDENT WHERE" + " s_address " + "like'" + str1 + "%'";
				List lst3 = DBDriverFactory.getDBDriverInstance().query(strSQL3, new MapListHandler());
				printList(lst3);
				break;

			case 5:
				System.out.println("请输入学生年龄？");
				String str2 = sc.next();
				String strSQL4 = "SELECT * FROM JWSTUDENT WHERE s_age='" + str2 + "'";
				List lst4 = DBDriverFactory.getDBDriverInstance().query(strSQL4, new MapListHandler());
				printList(lst4);
				break;

			}
			int b = sc.nextInt();
			if (b == 0) {
				break;
			}
		}
	}
	
	public void num(){
		System.out.println("shuru ziji ");
		
	}

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
