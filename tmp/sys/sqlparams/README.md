#	json形式查询的设计与解析

对json形式的查询条件，解析成对应sql中的where条件部分。即有点类似mongodb的json查询的设计

例子：

-	1	最简单的方式
	
		{
			"a.id@>":12
		}

	解析后的sql条件为： a.id>12

-	2	模糊查询

		{
			"a.name@like":"%lg%"
		}

	解析后的sql条件为： a.name like '%lg%'

-	3	in not 类似的查询

		{
			"a.id@in":[1,2,3]
		}

	解析后的sql条件为： a.id in (1,2,3)

-	4	时间查询

		{
			"a.updateTime@time>":"2015-3-1"
		}

	解析后的sql条件为 ： unix_timestamp(a.updateTime) > 1425139200

-	5	多条件查询，条件之间默认是and的关系

		{
			"a.id@>":12,
			"b.name":"lg"
		}

	解析后的sql条件为： a.id>12 and b.name='lg'

-	6	多条件嵌套查询，$or 表示里面的查询条件是 or 的关系

		{
			"c.age@>":14,
			"$or":{
				"b.id@=":2,
				"d.age@<":23
			}
		}

	解析后的sql条件为： c.age>14 and (b.id=2 or d.age<23)

-	7	复杂的多条件嵌套查询

		{
			"a.name":"lg",
			"$or":{
				"b.age@>=":24,
				"c.id":1,
				"$and":{
					"a.id@>":12,
					"b.id@<":34
				}
			}
		}

	解析后的sql条件为 ： a.name='lg' and (b.age>=24 or c.id=1 or (a.id>12 and b.id<34) )

#案例

对于json形式的查询，可以先转化成map结构的参数，然后使用如下：

	DefaultSqlParamsHandler defaultSqlParamsHandler=new DefaultSqlParamsHandler();
	String sqlWhere=defaultSqlParamsHandler.getSqlWhereParams(map结构的参数))；

上述方式直接输出对应的sql拼接形式，即 a.id>24 这样的一个字符串结果

如果想使用占位符，则使用如下：

	DefaultSqlParamsHandler defaultSqlParamsHandler=new DefaultSqlParamsHandler();
	SqlParamsParseResult sqlParamsParseResult=defaultSqlParamsHandler.getSqlWhereParamsResult(map结构的参数);
	System.out.println("testNormal:"+sqlParamsParseResult.getBaseWhereSql());
	System.out.println("testNormal:"+sqlParamsParseResult.getArguments());

解析结果的类型为：SqlParamsParseResult，它的baseWhereSql属性是 a.id>? 这样的字符串，它的arguments则是参数集合如 [24]

测试案例1如下：	

	@Test
	public void testNormal(){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("a.name","lg");
		params.put("b.age@>=",2);
		System.out.println("--------------------------------------");
		System.out.println("testNormal:"+defaultSqlParamsHandler.getSqlWhereParams(params));
		SqlParamsParseResult sqlParamsParseResult=defaultSqlParamsHandler.getSqlWhereParamsResult(params);
		System.out.println("testNormal:"+sqlParamsParseResult.getBaseWhereSql());
		System.out.println("testNormal:"+sqlParamsParseResult.getArguments());
	}

输出结果为：

	testNormal:b.age >= 2 and a.name = 'lg'
	testNormal:b.age >= ? and a.name = ?
	testNormal:[2, 'lg']
	
测试案例2如下：

	@Test
	public void testAndOr(){
		Map<String,Object> params=new HashMap<String,Object>();
		Map<String,Object> sonParams=new HashMap<String,Object>();
		sonParams.put("b.name","李四");
		sonParams.put("c.age@<",4);
		
		params.put("a.name","lg");
		params.put("$or",sonParams);
		System.out.println("--------------------------------------");
		System.out.println("testAndOr:"+defaultSqlParamsHandler.getSqlWhereParams(params));
		SqlParamsParseResult sqlParamsParseResult=defaultSqlParamsHandler.getSqlWhereParamsResult(params);
		System.out.println("testAndOr:"+sqlParamsParseResult.getBaseWhereSql());
		System.out.println("testAndOr:"+sqlParamsParseResult.getArguments());
	}


输出结果为：

	testAndOr:(c.age < 4 or b.name = '李四') and a.name = 'lg'
	testAndOr:(c.age < ? or b.name = ?) and a.name = ?
	testAndOr:[4, '李四', 'lg']
	
测试案例3如下：

	@Test
	public void testAndOrComplex(){
		Map<String,Object> params=new HashMap<String,Object>();
		Map<String,Object> sonParams=new HashMap<String,Object>();
		Map<String,Object> sonSonParams=new HashMap<String,Object>();
		
		sonSonParams.put("d.name","王五");
		sonSonParams.put("e.age@>=",12);
		
		sonParams.put("b.name","李四");
		sonParams.put("c.age@<",4);
		sonParams.put("$and",sonSonParams);
		
		params.put("a.name","lg");
		params.put("$or",sonParams);
		System.out.println("--------------------------------------");
		System.out.println("testAndOrComplex:"+defaultSqlParamsHandler.getSqlWhereParams(params));
		SqlParamsParseResult sqlParamsParseResult=defaultSqlParamsHandler.getSqlWhereParamsResult(params);
		System.out.println("testAndOrComplex:"+sqlParamsParseResult.getBaseWhereSql());
		System.out.println("testAndOrComplex:"+sqlParamsParseResult.getArguments());
	}

输出结果为：

	testAndOrComplex:((e.age >= 12 and d.name = '王五') or c.age < 4 or b.name = '李四') and a.name = 'lg'
	testAndOrComplex:((e.age >= ? and d.name = ?) or c.age < ? or b.name = ?) and a.name = ?
	testAndOrComplex:[12, '王五', 4, '李四', 'lg']


#自定义扩展
@> @in @time> 这些都是内部定义的一些操作符。如果用户想使用自定义的操作符，可以如下操作：

-	第一步： 实现SqlParamsParser接口，实现自己的解析器

-	第二步： 向DefaultSqlParamsHandler注册自定义的解析器，即调用它的registerSqlParamsHandler(SqlParamsParser sqlParamsParser)方法即可

具体怎么实现，参考其中的实现类（还是很简单的）。
目前内置的解析器接口及其实现类如下：
![SqlParamsParser接口类图][1]

 [1]: http://static.oschina.net/uploads/space/2015/0329/231450_TL3t_2287728.png
