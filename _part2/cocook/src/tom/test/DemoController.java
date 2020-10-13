package tom.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tom.cache.EHCacheManager;
import tom.cocook.annotation.ContentType;
import tom.cocook.annotation.Handler.Controller;
import tom.cocook.annotation.Handler.Path;
import tom.cocook.annotation.Handler.Resource;
import tom.cocook.annotation.Response.InputstreamOutput;
import tom.cocook.annotation.Response.JSONOutput;
import tom.cocook.core.ControllerModel;
import tom.cocook.core.RequestContext;
import tom.cocook.jdbc.simple.DBUtil;
import tom.kit.RandomNumImage;
import tom.kit.json.JSONException;
import tom.kit.json.JSONUtil;
@Controller
public class DemoController extends BaseAction{
	@Resource(DemoService.class)
	DemoService demoService;
	
	@Path("/flt/{user}")
	public String test1(HttpServletRequest req, Map<String, String> map, String user) {
		req.setAttribute("user", user);
		HashMap<String, Object> lmap = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();
		list.add("常州");
		list.add("苗侨");
		lmap.put("name", "panmg");
		lmap.put("list", list);
		req.setAttribute("mapL", lmap);
		return "freemarker";
	}
	
	@InputstreamOutput(ContentType.Image)
	@Path("/image")
	public Object image(HttpServletResponse res, Map<String, String> map) {
		res.setContentType("image/jpg");
		RandomNumImage randomNumImage = RandomNumImage.getInstance();
		return randomNumImage.getImage();
	}
	
	@Path("/json/{user}")
	@JSONOutput
	public Object json(HttpServletRequest req, UserD userD, String user, Map<String, String> map) throws JSONException {
		System.out.println(userD);
		System.out.println(user);
		System.out.println(map);
		
		List<?> list = DBUtil.getList("select * from aa");
		list =  (List<?>) JSONUtil.deserialize(JSONUtil.serialize(list)); //反序列化
		
		System.out.println(list);
		return list;   //序列化 
	}
	
	
	@Path("/vm/{user}")
	public String test(HttpServletRequest req, UserD userD, String user, Map<String, String> map) {
		System.out.println("req===="+req);
		System.out.println("map===="+map);
		System.out.println("user===="+user);
		System.out.println("userD===="+userD);
		
		
//		System.out.println("list<map>=="+DBUtil.getList("select * from aa"));
//		System.out.println("map=="+DBUtil.getMap("select * from aa where name=?", "panmg1"));
//		System.out.println(DBUtil.getList("select * from aa where name=?", String.class, "panmg1"));
//		System.out.println(DBUtil.getString("select value from aa where name=?", "panmg1"));
//		System.out.println(DBUtil.getInt("select value from aa where name=?", "a"));
//		
//		/* getBeans() 没加, 已取消次方法, 返回多个bean的集合*/
//		
//		
//		// 返回自增id,没有返回成功的条数
//		//System.out.println(DBUtil.insert("insert into aa values(?,?)", "wangjun2", "27"));
//		// 返回 成功插入的 条数
//		//System.out.println(DBUtil.exec("insert into aa values(?,?)", "ppp,","tttt"));
//		
//		HashMap<String, Object> aa = new HashMap<String, Object>();
//		aa.put("name","panmg222");
//		aa.put("value","panmg222");
//		//参数为 实体[支持map和bean], 表名
//		//System.out.println(DBUtil.insertDB(aa, "aa"));
//		System.out.println(DBUtil.updateDB(aa, "aa", "name='panmg'"));
//		
//		AA aa1 = new AA();
//		aa1.setName("panmg333");
//		aa1.setValue("panmg33");
//		//System.out.println(DBUtil.insertDB(aa1, "aa"));
//		System.out.println(DBUtil.updateDB(aa1, "aa", "name='panmg12'"));
		
		
		
		req.setAttribute("user", user);
		HashMap<String, Object> lmap = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();
		list.add("常州");
		list.add("苗侨");
		lmap.put("name", "panmg");
		lmap.put("list", list);
		req.setAttribute("map", lmap);
		return "velocity";
	}
	
	
	public static class AA{
		private String name;
		private String value;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	
	
	public static class UserD{
		private String user;
		private Integer age;
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		@Override
		public String toString() {
			return "UserD [user=" + user + ", age=" + age + "]";
		}
		
		
	}

}
