package org.lanqiao.tjut.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;
import org.lanqiao.tjut.db.DBDriverFactory;

public class TeaFuction {
	Scanner input = new Scanner(System.in);

	public void t() {
		while (true) {
			System.out.println("*****老师系统登入中*****");
			TeaFuction tf = new TeaFuction();
			// 判定老师是否登录
			boolean isF = tf.teacherenter();
			if (isF) {
				System.out.println("欢迎登入老师系统！");
				// 调用老师行为方法
				tf.doTF();

			} else {
				System.out.println("1重新登录    2退出");
				int a = input.nextInt();
				if (a == 2) {
					break;
				}
			}
		}
	}

	public boolean teacherenter() {
		System.out.println("请输入老师账号：");
		Scanner input = new Scanner(System.in);
		String strID = input.next();
		System.out.println("请输入老师密码：");
		String strNum = input.next();
		String strSql = "select * from JWTeacher  where teacher_id=? and teacher_num=?";
		List lst = (List) DBDriverFactory.getDBDriverInstance().query(strSql, new MapListHandler(),
				new Object[] { strID, strNum });
		// 判断一下该老师是否存在
		if (lst.size() > 0 && lst != null) {
			return true;
		} else {
			return false;
		}
	}

	@Test
	public void doTF() {

		System.out.println("1 添加学生信息");
		System.out.println("2 查询学生信息");
		System.out.println("3 修改学生信息");
		System.out.println("4 删除学生信息");
		System.out.println("0 任意键退出");
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		switch (a) {

		case 0:
			break;
		case 1:
			while (true) {
				System.out.println("请添加学生信息");
				System.out.println("学生账号");
				String strSnum = sc.next();
				System.out.println("学生密码");
				String strScode = sc.next();
				System.out.println("学生姓名");
				String strSname = sc.next();
				System.out.println("性别");
				String strSsex = sc.next();
				System.out.println("年龄");
				String strSage = sc.next();
				System.out.println("电话");
				String strSpho = sc.next();
				System.out.println("地址");
				String strSadress = sc.next();

				String strSql = "INSERT INTO JWSTUDENT(S_NUM,S_CODE,S_NAME,S_SEX,S_AGE,S_PHO,S_ADRESS,S_CON)VALUES(?,?,?,?,?,?,?,1)";
				DBDriverFactory.getDBDriverInstance().update(strSql,
						new Object[] { strSnum, strScode, strSname, strSsex, strSage, strSpho, strSadress });
				System.out.println("是否继续：['0'停止添加]");
				String strInput = sc.next();
				if (strInput.equals("0")) {
					// 退出
					break;

				}
			}
			// 查詢學生信息
		case 2:
			chaxun();
			break;
		// 修改学生信息
		case 3:
			while (true) {
				System.out.println("学生信息：");
				String strSql = "SELECT * FROM JWStudent";
				List lst = (List) DBDriverFactory.getDBDriverInstance().query(strSql,
						new BeanListHandler(JWStudentEntity.class));
				printList(lst);
				System.out.println("请输入修改的学生的id");
				String s_id = sc.next();
				System.out.println("请输入修改该学生的哪个信息");
				String str = sc.next();

				System.out.println("修改为：");
				String str1 = sc.next();

				String str2 = "UPDATE  JWSteudent SET " + str + "=? WHERE s_id=?";
				int i = DBDriverFactory.getDBDriverInstance().update(str2, new Object[] { str1, s_id });
				System.out.println(i > 0 ? "数据修改成功" : "数据修改失败");
				System.out.println("1继续修改学生信息     2退出");
				int m = sc.nextInt();
				if (m == 2) {
					break;
				}
			}
			break;
		case 4:
			while (true) {
				System.out.println("学生信息：");
				String strr = "SELECT * FROM JWSTUDENT where s_con<>0";
				List lst = (List) DBDriverFactory.getDBDriverInstance().query(strr,
						new BeanListHandler(JWStudentEntity.class));
				printList(lst);
				if (lst != null && lst.size() > 0) {
					System.out.println("请输入删除的学生的s_num");
					String stu_num = input.next();
					String strSql = "update ZSSTUDENT set s_con=0 where s_num=?";
					int i = DBDriverFactory.getDBDriverInstance().update(strSql, new Object[] { stu_num});
					System.out.println(i > 0 ? "数据删除成功" : "数据删除失败");
				} else {
					System.out.println("没有可删除的学生");
				}
				System.out.println("【1】继续删除学生---【2】退出");
				int v = input.nextInt();
				if (v== 2) {
					break;
				}
			}
		}
	}

	// 查询
	@Test
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
				System.out.println("请输入你要查询的学生学号：");
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

	/**
	 * 
	 * @param b
	 * @param str
	 */
	private void updatestudent(String b, String str) {
		Scanner input = new Scanner(System.in);
		System.out.println("修改为：");
		String str1 = input.next();
		String strSQL3 = "UPDATE  JWSTUDENT SET " + b + "=? WHERE S_NUM=?";
		int l = DBDriverFactory.getDBDriverInstance().update(strSQL3, new Object[] { str1, str });
		System.out.println(l > 0 ? "数据修改成功" : "数据修改失败");
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
