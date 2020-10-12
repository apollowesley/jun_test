#restful-tester

##项目说明
&nbsp;&nbsp;&nbsp;&nbsp;目前大部分的restful接口测试工具，仅仅停留在验证接口响应是否正常，具体的业务是否正确需要靠人工来分析。人工分析势必比较耗时，而且随着项目的迭代，重复工作量的浪费比较严重。因此，考虑到接口的变动会较少，自动化接口测试将很有必要。

##项目技术实现
* 采用[HttpClient](http://hc.apache.org/)实现接口的调用，支持GET/PUT/POST/DELETE方式，支持目前Restful接口规范常用的Http方法；
* 自动化脚本的编写采用[Cucumber](https://cucumber.io/)来实现，脚本编写的时候分为Feature、Scenario以及Step。每个Feature下可以包含N个场景，每个场景下有M个步骤，和实际测试中的测试用例场景贴近；
* 结果验证采用Javascript Engine，由于Javascript支持弱语言，因此在处理上兼容性会更好，具体代码如下：

```java
public class ExecuteEngine {

    private static ScriptEngineManager ENGIEN_FACTORY = new ScriptEngineManager();
    private static ScriptEngine        JS_ENGINE      = ENGIEN_FACTORY
            .getEngineByName("JavaScript");

    public static Boolean execute(String expression) throws Exception {
        String parseExpression = ExpressionParser.parse(expression).toString();
        Boolean result = (Boolean) JS_ENGINE.eval(parseExpression);

        return result;
    }

}
```

##脚本编写说明
* 在src/main/resources目录下增加org/simplestudio/restful/模块名称的目录，其中模块名称为具体要测试的业务模块，比如备忘模块，审批模块；
* 在新增的目录下，以模块名称为文件名称，增加.properties文件，里面配置好测试的接口url以及变量（如果有需要的话）；
* 在新增的目录下，增加.feature的文件，文件的规范参照Cucumber官方的说明。具体本框架支持的Step详见Step说明；

##支持的Step说明
* Given 使用用户名=[xxx]登录系统
* 采用[POST]方式请求URL[http://www.baidu.com],不传参数,不记录返回值
* 采用[PUT]方式请求URL[xxx],不记录返回值
* 采用[GET]方式请求URL[xxx],不传参数,并记录返回值为[xxx]
* 采用[PUT]方式请求URL[xxx],并记录返回值为[xxx]
* 打印结果,打印的记录值为[xxx]
* 进行结果校验,表达式为[xxx]

##例子
###common.properties
```properties
#模块名称,名称需要和feature的文件目录结构保持一致
moduleName=kuaidi100

#要执行的feature顺序，同个feature可以多次执行，多个以逗号隔开。不配置按照cucumber默认的解析顺序执行
run.feature.orderlist=快递100接口测试

#增加的plugin列表，多个以逗号隔开.默认会强制生成excel的报告
add.plugin.list=html:target/html
```

###kuaidi01.properties
`
快递ID=700259627563
快递类型=yuantong
查询存在的快递=http://www.kuaidi100.com/query?type=${快递类型}&postid=${快递ID}&id=1&valicode=&temp=0.7655106628875419
查询不存在的快递=http://www.kuaidi100.com/query?type=${快递类型}&postid=100259627562&id=1&valicode=&temp=0.7655106628875419
`

####kuaidi01.feature
`
Feature: 快递100接口测试

    Scenario: 查询-正常情况
		When 采用[GET]方式请求URL[${查询存在的快递}],不传参数,并记录返回值为[快递信息]
		Then 进行结果校验,表达式为[${快递信息.status} == 200]
		Then 进行结果校验,表达式为[${快递信息.message} == 'ok']
		Then 进行结果校验,表达式为[${快递信息.nu} == 700259627563]
		
	Scenario: 查询-异常情况
		When 采用[GET]方式请求URL[${查询不存在的快递}],不传参数,并记录返回值为[快递信息]
		Then 进行结果校验,表达式为[${快递信息.status} == 201]
		Then 进行结果校验,表达式为[${快递信息.message} == '快递公司参数异常：单号不存在或者已经过期']
`

##运行结果
![运行结果](http://git.oschina.net/zzq0324/restful-tester/raw/master/result.png?dir=0&filepath=result.png&oid=ffccf32dc2f85035d777b087d0337283f522adde&sha=8ebc5c074c719ba096dbfa3044361cd91a26406d)