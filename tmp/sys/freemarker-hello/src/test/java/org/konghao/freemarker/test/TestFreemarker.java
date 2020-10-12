package org.konghao.freemarker.test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.konghao.freemarker.FreemarkerUtil;
import org.konghao.freemarker.model.Article;
import org.konghao.freemarker.model.Channel;
import org.konghao.freemarker.model.Student;
import org.konghao.freemarker.model.User;

public class TestFreemarker {
	FreemarkerUtil fu;
	Map<String,Object> root = null;
	
	@Before
	public void setUp() {
		fu = new FreemarkerUtil();
		root = new HashMap<String,Object>();
	}
	
	@Test
	public void test01() {
		//1、创建数据模型
		Map<String,Object> root = new HashMap<String,Object>();
		//2、为数据模型添加值
		root.put("username", "张三");
		//3、将数据模型和模板组合的数据输出到控制台
		fu.print("01.ftl", root);
		fu.fprint("02.ftl", root, "01.html");
	}
	
	@Test
	public void test02() {
		//freemarker还可以输出相应的对象
		root.put("user", new User(1,"李四",16));
		sprint("03.ftl");
		fprint("03.ftl","03.html");
	}
	
	@Test
	public void test04() {
		List<User> users = Arrays.asList(new User(1,"张三",22),new User(2,"李四",33));
		root.put("users",users);
		sprint("04.ftl");
		fprint("04.ftl","04.html");
	}
	
	@Test
	public void test05() {
		root.put("username", "管理员");
		List<User> users = Arrays.asList(new User(1,"张三",22),new User(2,"李四",33));
		root.put("users",users);
		sprint("05.ftl");
		fprint("05.ftl","05.html");
	}
	
	@Test
	public void test06() {
		
		//此时user对象并没有group的值，这时如果在页面显示group直接报错
		//freemarker不会处理空值
		root.put("user",new User(1,"地点",22));
		sprint("06.ftl");
	}
	
	@Test
	public void test07() {
		root.put("now",new Date());
		root.put("username", "李四");
		sprint("07.ftl");
		fprint("07.ftl", "07.html");
	}
	
	@Test
	public void test08() {
		sprint("08.ftl");
	}
	
	@Test
	public void test09() {
		sprint("09.ftl");
	}
	
	@Test
	public void test10() {
		root.put("username","张三");
		sprint("10.ftl");
	}
	
	@Test
	public void test11() {
		sprint("11.ftl");
	}
	
	@Test
	public void test12() {
		List<User> users = Arrays.asList(new User(1,"张三",22),
						new User(2,"李四",33),
						new User(3,"王五",44));
		root.put("users",users);
		List<Student> stus = Arrays.asList(new Student("123123", "地点"),new Student("11111","方法"));
		root.put("stus", stus);
		sprint("12.ftl");
		fprint("12.ftl","12.html");
	}
	
	@Test
	public void test13() {
		sprint("13.ftl");
		fprint("13.ftl","13.html");
	}
	
	@Test
	public void test14() {
		Map<String,List<Article>> arts = new HashMap<String,List<Article>>();
		List<Article> a1 = Arrays.asList(new Article("说给焦点方法各家阿斯顿发贺卡就是地方贺卡设计", new Channel(1, "多对多")),
						new Article("看似简单干净啊", new Channel(1, "多对多")),
						new Article("阿斯达卡说得好斯蒂芬斯蒂芬", new Channel(1, "多对多")),
						new Article("阿阿斯顿撒上所说的话", new Channel(1, "多对多")),
						new Article("是地方贺卡设计", new Channel(1, "多对多")),
						new Article("斯诺克打击法轮功建设的", new Channel(1, "多对多")));
		arts.put("1", a1);
		
		List<Article> a2 = Arrays.asList(new Article("你说的就是公司根据阿卡什打电话", new Channel(1, "多对多")),
				new Article("个撒旦发撒旦发的时候", new Channel(1, "多对多")),
				new Article("个很好的方法斯蒂芬斯蒂芬个", new Channel(1, "多对多")),
				new Article("阿撒旦发撒旦发上所说的话", new Channel(1, "多对多")),
				new Article("是地方贺卡设计阿斯达卡的方法撒旦发撒旦发", new Channel(1, "多对多")),
				new Article("阿斯顿发撒旦发撒旦发撒旦和斯诺克打击法轮功建设的", new Channel(1, "多对多")));
		arts.put("2", a2);
		root.put("arts",arts);
		sprint("14.ftl");
		fprint("14.ftl","14.html");
	}
	
	private void sprint(String name) {
		fu.print(name, root);
	}
	private void fprint(String name,String filename) {
		fu.fprint(name, root, filename);
	}
}
