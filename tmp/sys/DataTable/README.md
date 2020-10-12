# DataTables  jq插件  的服务器端参数解析工具



> 分页，即时搜索和排序
> 几乎支持任何数据源：DOM， javascript， Ajax 和 服务器处理
> 支持不同主题 DataTables, jQuery UI, Bootstrap, Foundation
> 各式各样的扩展: Editor, TableTools, FixedColumns ……
> 丰富多样的option和强大的API
> 支持国际化

DataTables 插件的使用  [点击](http://datatables.club/)  
 **需要开启服务器模式** 

##使用方式  

可以使用spring mvc  注解方式 或者 使用DataTableUtils 工具解析参数
 **使用spring mvc 注解方式需要 配置 注解的解析类** 


```xml
         <mvc:annotation-driven>
		<mvc:argument-resolvers>  
                    <bean class="cc.yihy.utils.DataTableResolver"/>  
                </mvc:argument-resolvers>  
		
	 </mvc:annotation-driven>
```


##简单使用示例

``` java
	/**
	 * 使用spring mvc  处理Datatable的参数   使用注解
	 * @param tableRequest  DataTable 请求参数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public DataTableResponse<User> getData(@DataTableParam DataTableRequest tableRequest) throws Exception{
		
		//对请求参数解析，生成 排序条件、列搜索对象、全局搜索对象
		ResultObj<User> resultObj = tableRequest.getResultObj(User.class);
		//dataTable 相应参数   会被处理成json
		DataTableResponse<User>  user= new DataTableResponse<User>();
		
		return user;
	}
	
	@RequestMapping("list1")
	public DataTableResponse<User> getList(HttpServletRequest request) throws Exception{
		//不使用注解
		
		DataTableRequest param = DataTableUtils.getParam(request);
		
		//对请求参数解析，生成 排序条件、列搜索对象、全局搜索对象
		ResultObj<User> resultObj = param.getResultObj(User.class);
		//dataTable 相应参数   会被处理成json
		DataTableResponse<User>  user= new DataTableResponse<User>();
		
		return user;
	}
```

如果使用了  mybatis，搭配使用 PageHelper插件 配合我这个datatable插件是很方便的


代码也比较简单 实现请看里面代码