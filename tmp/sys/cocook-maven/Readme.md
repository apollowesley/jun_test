## cocook 集成　MVC　boot　启动,servlet规范COC  &&　 直连 RPC

cocook 升级 2.0,对项目进行重构优化
##新版功能:
### boot启动方式, 内置tomcat, Undertow, 优化性能

```java 
public class BS_API_MAIN {
	public static void main(String[] args) throws ServletException {
		new UndertowStart(BS_API_MAIN.class).init(args)
		.addCocookServlet()
		//.addServlet(statViewServlet).addFilter(webStatFilter)
		.addlistener(WebEmbedListener.class)
		.start();
	}
}
```
undertow 使用阿里云 4核8G 测试性能, 大约1W4, 默认jvm, 无优化
```
[root@iZm5ej7x3ylo95t7fgxamyZ ~]# ab -c 200 -n 10000  http://10.30.165.96:7070/get
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 10.30.165.96 (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests


Server Software:        
Server Hostname:        10.30.165.96
Server Port:            7070

Document Path:          /get
Document Length:        41 bytes

Concurrency Level:      200
Time taken for tests:   0.677 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      2870287 bytes
HTML transferred:       410041 bytes
Requests per second:    14777.58 [#/sec] (mean)
Time per request:       13.534 [ms] (mean)
Time per request:       0.068 [ms] (mean, across all concurrent requests)
Transfer rate:          4142.18 [Kbytes/sec] received
```


### 新增 内置 NIO, AIO Server, 主要用于 内部 直连RPC, 接口拷贝 znet 设计
```java
public static void main(String[] args) throws Exception {
		AioServer server = new AioServer(8080);
		MessageAdaptorServer adaptorServer= new MessageAdaptorServer();
		adaptorServer.setReadWritebufferSize(10*1024);
		adaptorServer.registerHandler("test", new MessageHandler() {
			@Override
			public void handleMessage(HttpMessage msg, Session sess) throws IOException {
				msg.setStatus("200");
				msg.setBody("hello");
				sess.write(msg);
			}
		});
		server.setIoAdaptor(adaptorServer);
		
		server.start();
	}
```
AIO内置server hello word 测试性能, 使用阿里云 4核8G 测试性能, 大约6W
此内网测试 http 协议, 真正使用方式 为rpc长连接, 并非用于httpserver 服务器, 
内网长连接方式 QPS 50~100W左右
```
[root@iZm5ej7x3ylo95t7fgxamyZ ~]# ab -k -c 300 -n 100000  http://10.30.165.96:8080/test
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 10.30.165.96 (be patient)
Completed 10000 requests
Completed 20000 requests
Completed 30000 requests
Completed 40000 requests
Completed 50000 requests
Completed 60000 requests
Completed 70000 requests
Completed 80000 requests
Completed 90000 requests
Completed 100000 requests
Finished 100000 requests


Server Software:        
Server Hostname:        10.30.165.96
Server Port:            8080

Document Path:          /test
Document Length:        5 bytes

Concurrency Level:      300
Time taken for tests:   1.519 seconds
Complete requests:      100000
Failed requests:        0
Write errors:           0
Keep-Alive requests:    100000
Total transferred:      13416616 bytes
HTML transferred:       500620 bytes
Requests per second:    65827.89 [#/sec] (mean)
Time per request:       4.557 [ms] (mean)
Time per request:       0.015 [ms] (mean, across all concurrent requests)
Transfer rate:          8624.88 [Kbytes/sec] received
```




直接web.xml使用介绍: 
web.xml 配置
```<servlet>
		<!-- 定义Servlet名称 -->
		<servlet-name>cocookServlet</servlet-name>
		<!-- Servlet具体实现类 -->
		<servlet-class>tom.cocook.handler.CocookServlet</servlet-class>
		<!-- 初始化上下文对象 -->
		<init-param>
			<!-- 参数名称 -->
			<param-name>SystemConfigLocation</param-name>
			<!-- 加载配置文件 -->
			<param-value>/WEB-INF/config/app.properties</param-value>
		</init-param>
		<!-- 设置启动的优先级 -->
		<load-on-startup>1</load-on-startup>
</servlet>
```
app.properties 中 用三项项必须配置, 其他参照 git上传的文件
```
#dataSource 配置文件路径, 文件路径只支持 WebRoot路径, 不支持class路径, 未实现
DBConfigLocation = /WEB-INF/config/DBConfig.properties

#扫描包 必须填写
scanPackage = tom.test

#handler 必须填写  cocook提供两种模式DefaultNameHandler(类似springCoC模式), DefaultAnnotationHandler(@path url自定义模式)
handler = tom.cocook.handler.DefaultNameHandler,tom.cocook.handler.DefaultAnnotationHandler

#可省略-----编码  默认 UTF-8
#encoding=UTF-8

#可省略-----page目录  默认 /WEB-INF/page 
#pageContext =/WEB-INF/page

#可省略默认.htm-----page后缀  
#pageSuffix =.htm


#可省略-----log4j配置文件路径 
#log4jConfigLocation = /WEB-INF/config/log4j.properties

#可省略默认velocity -----视图配置 支持 velocity, freemarker,jsp
#viewType = velocity
#viewConfigLocation = /WEB-INF/config/velocity.properties
```

代码示例: DemoController ---> xxxController extends ControllerModel --> 遵循COC
```java
@Controller
public class DemoController extends ControllerModel{
	@Resource(DemoService.class)
	DemoService demoService;
	
	@Override
	public boolean before(RequestContext resContext, Map<String, String> map) {
		resContext.setAttribute("cxtpath", resContext.getRequest().getContextPath());
		return super.before(resContext, map);
	}
	
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
	@JSONOutput
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
	
}
```

拦截器示例: DemoInterceptor extends AbstractInterceptor
```java
// 设置handler 绑定, 全局绑定,默认绑定DefaultNameHandler.class
@Interceptor(handler={DefaultNameHandler.class}) //,DefaultAnnotationHandler.class 
public class Demointerceptor extends AbstractInterceptor{

	@Override
	public boolean before(Serializable actionInvocation) {
		System.out.println("------before 拦截器执行  begin");
		ActionInvocation invocation = (ActionInvocation)actionInvocation;
		System.out.println("params====="+ invocation.getParaMap());
		System.out.println("url   ====="+invocation.getMethodUrl());;
		System.out.println("urlmethod=="+invocation.getHandlerInfo().getMethods().get(invocation.getMethodUrl()));
		System.out.println("contor====="+invocation.getHandlerInfo().getController());
		System.out.println("urlpams===="+invocation.getHandlerInfo().getUrlParams());
		System.out.println("------before 拦截器执行  end ");
		return true;
	}
} 
```