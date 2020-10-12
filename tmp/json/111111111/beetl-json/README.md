![beetl-json-logo.jpg](beetl-json-logo.jpg)
##介绍：

－ 作者：闲.大赋（同时也开发了Beetl模版语言）
－ 功能：Beetl-JsonTool 作为beetl模板引擎的一个附属工具，提供了对象序列化成json技术。其原理是基于(Location:action)*，功能强大，扩展性强，比fastjson,jackson,gosn基于annotatoin的强大，也远远超过了jodd json,flexjson序列化能力 而体积小，仅仅不到80K。它能允许通过序列化策略来个性化的序列化对象到JSON而无需编程或者包装原有对象，而它的性能也是相当优秀。

##例子:

* api 例子如下  
  
        JsonTool tool = new JsonTool();
    	String json = tool.serialize(user);			
     	String json1 = tool.serialize(user，"id:i"));		//忽略属性       
    	String json2 = JsonTool.serialize(list，"[1].id:i"));			//忽略第二个元素的id属性
    	//所有属性都会执行hinernateCheck的回调
    	String json2 = JsonTool.serialize(obj,"*:!hibernate"));
		
		
* 序列化策略的简单例子：		
	* name:i 忽略name属性
	* id:i,obj.id:i 忽略属性id,忽略属性obj对象的id的属性.可以将多个序列化策略组合在一起，用逗号分开
	* name:nn/myName/ 将属性name输出成myName
	* ~L/com.test.User/:o/name, age/ User对象实例排序输出，name,age 先输出，其他按照定义输出
	* [1].date:f/yyyy-MM-dd/  列表第二项目的date属性格式化输出
	
* 序列化规则

序列化规则可以有多个，每个包含一个location和action对，用冒号分开，每个序列化规则用逗号分开，这个类似json的格式，如
		name:i,user.id:i,~L/#ju.Collection*/:->null
如上表示三个序列化规则，
	*第一个是忽略属性name
	*第二个是忽略user属性的id属性
	*第三个要复杂一些，意思当序列到Collecton类及其子类时候，直接赋值为null(#jl是java.util的别名)。

location和action有可能出现//,这类似程序语言的(),里面是参数，如~L/#ju.Collection*/
	
##Action
定义了一个匹配动作的输出，有忽略属性，包含属性，排序，改名，条件判断，直接赋值，调用回调等

* i:表示忽略，如name:i 遇到name属性，忽略. ~*:i/age,bir/ 忽略类的属性age,bir，[1]:i 表示忽略第2项，[name]:i 表示忽略key为name的项 ，~L/org.beetl.json.Foo/:i ,类型为Foo的都忽略
。name和age属性总是排在最前面.其他属性按顺序排（内置的AttributeComparator完成属性排序，原始类型排在最前，其次是pojo类型，再次是java类型，最后是集合类型）
* u:表示使用类的属性，如~*:u/name,age,bir/。 其他属性忽略 
* nn 表示更改名字，{nane:nn/myName/}  将输出myName
* ? 代表一个if，如果满足条件，则指定一个输出，如xxList:?null->[],如果某集合xxList 为null，则输出一个[]
，?后年可以指定null，empty，以及某个数字。->表示输出
* $.xxx，用于调用当前位置对象的某个方法，如{ts:$.get},并不是输出ts,而是输出ts.get(). 调用后，可以使用->在使用一个Action，比如：
"~L/java.util.Calendar*/:$.getTime->f/yyyy-MM-dd/" 遇到Calendar和其子类，先获取Date类型，然后格式化
* f 用于格式化输出，如number:f/#.##/,也可以自定义格式化函数并注册，比如注册一个格式化函数Money,
则number:fMoney 则会调用money做输出
* ->  直接输出 ，比如 name:->'unkown'.
* !xxx  调用自定义Action，xxx为注册的自定义Action。
* f: 格式化日期或者数字类型，也可以调用使自定义格式化
* o:表示排序，如~L/xxxxClass/:o/name, age/ 
* asJson 直接输出对象toString方法，不加引号

* 当循环引用的时候,允许输出一些额外字段而不是单纯的引
	* cu:出现循环引用，仅仅显示参数指定的字段，如{~L/Department/*:ci/name,id/} ,对于Department类，如果被重复引用，则只显示name和id，其他字段不显示。
	* ci:出现循环引用，不显示参数指定的字段，如{~L/Department/*:ci/users/} ,对于Department类，如果被重复引用，则不显示users字段。



##循环处理

注意，当序列化出现循环引用的，beetl-json不会再列化已经序列化过的对象，取而代之是用$ref:path来指向上次序列化的对象，path从根目录开始，如

		user:{ "$ref":"$.list[0].user"}
		
如果同时还使用了ci，cu，可以选择性额外的输出一些属性，而不是枯燥的 "$ref":path,如:序列化规则是
~/User/:cu/name/ 意思当User对象出现在循环引用里，除了输出$ref外，还输出name属性。生成结果类似如下:

		user:{ "$ref":"$.list[0].user",name:'xiandafu'} 

这样使得输json更容易阅读,有些JS前端框架也可以利用额外的这些属性
 

##Location
定义了一个序列化的位置，如属性名，或者属性表达式，列表（数组）元素，类等

* 简单属性 如name:nn/myName/ 当前实例遇到name属性，输出的是myName，nn是action指示，表示New Name
* 复杂属性，如 user.age:->0 当前实例遇到user属性下的age属性，总是输出0.
* 属性正则表达式，如/id|name/ 当前实例所有id或着name属性
* \* 表示所有属性，如\*:!hibernate, 所有属性序列化前都会执行hibernate
* \*./reg/" 表示符合正则表达式的所有属性，如\*./id/, 所有属性名为id的。
* ~L  后面是个具体类，如~L/com.test.User/:u/id,name/ 在序列化过程中，所有类型为User的仅仅输出id和name。
	* 可以在类名后面使用*，表示也匹配子类，如~L/com.test.User*/
	* 可以通过调用JsonTool.addAlias("shortName", "longName");为包名增加别名。系统默认增加ju 代表java.util,jl代表java.lang,如果addAlias("com.test", "ct"),那么，~L/com.test.User*/可以简写为 ~L/#ct.User/
* [] 代表集合
	* [*],位置是集合所有元素
	* [1],位置是集合第二个元素
	* [xxkey] 集合是个Map，位置是xxKey对应的元素

* ~ 代表当前实例的某个类型:
	* ~d 遇到类型为日期的，格式化输出。d是Java.Util.Date的简称。
    * ~n 表示一个number类型    
    * ~c 表示是一个集合
    * ~d 表示一个日期
* ~\*：表示当前实例所有类型，即所有属性字段，如~*:o/age,name/，当前实例的属性，按照age,name先后顺序输出，其他保持不变
	
locaton 可以是属性表达式

* obj.aaa.xx,指属性obj的属性aa的属性xx的位置
* obj./.*str/ 指属性obj下的所有以str结尾的属性，.*str是正则表达式
* obj.~*  指属性obj下的所有属性
* obj[1].name ,指属性obj是个集合，位置是第二元素的name属性。
* obj[*].name ,指属性obj是个集合,位置是所有元素。
* ["key"].name ,指map的key项。
* obj.aaa.~* .指属性obj的属性aa,如obj.aaa.~*:i/users,orgs/ 
* [*].tt.~L/#ct.User/:i/users,orgs/  位置是集合元素的tt属性下的所有类型是User的对象，序列化仅仅输出users,和orgs

注意，目前不支持在~L/Class/后面继续用属性表达式
 


##API例子:

		//全局设定，对于所有对象都适用	
		JsonTool tool = new JsonTool();
		tool.addLocationAction("~d","f/yyyy.MM.dd/");	
		tool.addLocationAction("~L/java.util.Calendar*/","$.getTime->f/yyyy-MM-dd/");
		//类json格式的策略，用逗号分开多个locationAction
		tool.addPolicy("~f:n/#.##/,~c:?null->[]");
		// 默认是紧凑输出，使用true，将换行和缩进	
		tool.pretty = true;
		tool.addAlias("loc", "org.beetl.json.test.location");
		//序列化User
		String json = tool.serialize(User);
		//or  指定一个序列化策略,age，name先输出，适合有特殊需求的对象或者无法注解（第三方）对象
		String json2 = tool.serialize(User，"~*:o/age,name/"));
		// 同上策略，但name属性输出改为code
		String json2 = tool.serialize(User，"~*:o/age,name/,name:nn/code/"));
		// 同上策略，像api传递俩个策略
		String json2 = tool.serialize(User，"~*:o/age,name/","name:nn/code/"));
		//序列化dept,Department跟SysUser是一对多关系，如果俩个类都重复引用，则忽略导致重复引用的dept字段和users字段
		String json1 = tool.serialize(dept, "~L/#loc.SysUser/:ci/dept/,~L/#loc.Department/:ci/users/");

## 注解
默认情况下，也可以用注解来作为序列化策略。

	@Json(
		policys={
				@JsonPolicy(location="name", action="nn/newUserName/"),
				@JsonPolicy(location="deleteList", action="?empty->[]")						
		}
	)
	public class User{	
		String name="joel";
		int age =12;	
		double salary=12.32266;
		Customer  customer = new Customer();
		List<Customer>  list = new ArrayList<Customer>();
		List<Customer>  deleteList = null;
		//getter and setter 	方法必须有，在此忽略
	}



	@Json(
		policys={
				@JsonPolicy(location="name", action="nn/userName/")			
		}
	)
	class Customer{
		String name="lijz";
		int age=11;
		Date bir = new Date();
		//getter and setter 	方法必须有，在此忽略
	}

##Action扩展

允许自定义Action来处理序列化特殊需求，Action可以实现IValueAction，用来处理渲染对象，也可以实现IKeyAction，IInstanceAction等。
如下代码实现一个IValueAction，当检测到对象并未真正hibernate加载的时候，不序列化，从而避免hibernate懒加载问题

		
		JsonTool tool = new JsonTool();	
		tool.addAction("hibernate", new IValueAction() {
			
			@Override
			public int getIndex() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public ActionReturn doit(OutputNodeKey field, Object value,
					OutputNode thisNode, JsonWriter w) {
				if (value instanceof HibernateProxy) {// hibernate代理对象
	                LazyInitializer initializer = ((HibernateProxy) value).getHibernateLazyInitializer();
	                if (initializer.isUninitialized()) {
	                    return new ActionReturn(null,ActionReturn.RETURN);
	                }
	            } else if (value instanceof PersistentCollection) {// 实体关联集合
	                PersistentCollection collection = (PersistentCollection) value;
	                if (!collection.wasInitialized()) {
	                    return new ActionReturn(null,ActionReturn.RETURN);
	                } else if (collection.getValue() == null) {
	                    return new ActionReturn(null,ActionReturn.RETURN);
	                }
	            }
	            return new ActionReturn(value,ActionReturn.CONTINUE);
			}
		});
		
		String joins = "location,customer"
		List cars = service.findAll(joins);
		String json = tool.serialize(cars,"*:!hibernate");
		
##格式化扩展
可以自定义一个格式化函数，如下代码

		JsonTool.addFormat("simple", new MyDateFomrat());
		
		public class MyDateFomrat implements Format{
		
			public Object format(Object o) {
				// TODO Auto-generated method stub
				return "2015";
			}
		
		}
		//序列化item,其属性bir按照simple来格式化，f是acton前缀，simple是注册的格式化函数		
		String json1 = JsonTool.serialize(item, "bir:fsimple");



## 主要api：

* JsonTool.addLocationAction(String loc,String action)   :增加一个全局的序列化策略，如JsonTool.addLocationAction("~d","f/yyyy.MM.dd/");	
* JsonTool.pretty:默认是紧凑输出，改为true，则有缩进和换行
* JsonTool.serialize(Object o) :序列化对象，返回String
* JsonTool.serialize(Object o,Writer w) :序列化对象到Writer里
* JsonTool.serialize(Object o,String policy) :序列化对象，返回String,并使用额外的序列化策略
* JsonTool.serialize(Object o,String... policy) :序列化对象，使用多个序列化策略
* JsonTool.serialize(Object o,Writer w,String policy) :序列化对象到Writer里,并使用额外的序列化策略
* JsonTool.serialize(Object o,Writer w,String... policy) :序列化对象到Writer里,使用多个序列化策略
* JsonTool.addFormat(Class type, Format format) 注册一个格式化函数,然后可以通过f来格式化此属性，如xxx:f,f后面也可以使用参数，如xxx:f/yyy/,参数将传入Format类
* JsonTool.addAction("hibernate", new HibernateAction() ); 注册一个Action
* JsonTool.addAlias(String shortName,String name) 增加别名
* JsonTool.attributeErrorHander 当属性获取错误的时候如果处理，默认为抛出异常，也可以实现AttribiuteErrorHandler 实现自己的错误处理，如Hibernate lazy load 会抛错，可以设置此接口忽略错误
	

## SpringMVC集成：

配置如下:

		<mvc:annotation-driven>
	    <mvc:message-converters register-defaults="true">
	      <bean class="org.beetl.json.ext.BeetlJsonHttpMessageConverter">
	        <property name="supportedMediaTypes" value="text/html;charset=UTF-8"/>
	        <property name="policys">
	             <map>
	               <entry key="~d" value="f/yyyy-MM-dd/"/>
	            </map>
	        </property>
	      </bean>
	    </mvc:message-converters>
	  </mvc:annotation-driven>

代码例子

		@RequestMapping(value = "/json.html", method = RequestMethod.GET)
	    @ResponseBody
	    public List index1(HttpServletRequest req) {
	    	List list = new ArrayList();
	    	list.add(1);
	    	list.add(2);
	    	list.add(new Date());
	        return list;
	    }
	    
如果想自定义序列化策略，而不是用Annotaion，可以采用SerObject对象

		@RequestMapping(value = "/json2.html", method = RequestMethod.GET)
		@ResponseBody
		public SerObject index2(HttpServletRequest req) {
			List list = new ArrayList();
			list.add(1);
			list.add(2);
			list.add(new Date());
			SerObject s = new SerObject(list,"[1]:i"); //忽略第2个元素
		    return s;
		}


更多例子参考单元测试代码 https://git.oschina.net/xiandafu/beetl-json/tree/master/test

## maven 地址

	<dependency>
	    <groupId>com.ibeetl</groupId>
	    <artifactId>btjson</artifactId>
	    <version>0.94</version>
	</dependency>

## 下一步：

* 性能优化，争取前2，将增加asm优化，unsafe 优化
* 简单反序列化，总体大小不超过140k。

