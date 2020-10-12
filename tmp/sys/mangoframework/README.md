# mangoframework
简单的javaweb框架。 类似springMVC

## 为什么还要重复造轮子？
    1.springMVC 不在轻量级了，功能太多用不上。
    2.综合其他框架的优点造一款自己喜欢的风格。
    3.轮子简单造起来也不费力。

## 使用说明
需要classpath下创建mango.properties

web.xml中配置
````
    <servlet>
       <servlet-name>mangoservlet</servlet-name>
        <servlet-class>org.mangoframework.core.MangoDispatcher</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>mangoservlet</servlet-name>
        <!--<url-pattern>/test/*</url-pattern>-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```` 
## mango.properties配置说明

* 基本配置
mango.controller.package ：controller所属包名，如com.xxx.xx.controller


* 文件大小
mango.filesize.max ：上传单个文件最大值  默认1024000（1M）
mango.size.max ：多个文件总共最大值 默认：40960000（默认4M）

* 其他 
mango.errorpage ：全局错误页面地址
mango.safe.http : 是否启用安全http。选项：disabled/yes；默认disabled
mango.escape：是否过滤xss。选项true/false

* 过滤器
mango.filter.com.xxx.xx.filter.AuthorityFilter=true  需要实现 org.mangoframework.core.dispatcher.MangoFilter


* 处理器
mongo.view.xxx=完整包名.类名。如：
 * json后缀处理器 mango.view.json=cn.com.xxx.xx.view.JsonView
 * html后缀处理器 mango.view.html=org.mangoframework.core.view.JspView(/WEB-INF/web,.jsp) 
 * 无后缀处理器 mango.view.default=org.mangoframework.core.view.JspView(/WEB-INF/jsp,.jsp)


### 注解使用
* @RequestParam ：请求参数，用于Method中
        @Get(value = "{id}", template = "/api")
        public Object get(@RequestParam("id") String id, Parameter parameter) {...}
* @RequestMapping ：请求映射，可用于Class、Method 
    * value: 映射地址，默认"" ，支持通配符{id}，
    * method: 默认支持全部
    * template: 模版地址 当使用jsp或者freemark是需要该字段
    * singleton：单例模式。默认否
* @Get，@Post，@Put，@Delete == @RequestMapping(method ={RequestMethod.GET} )

### 参数注入
* Parameter 
* HttpServletRequest
* HttpServletResponse
* 参数通配符，需要RequestParam注解