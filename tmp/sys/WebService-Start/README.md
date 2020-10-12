# WebService-Start
WebService3.2.1入门到精通
#### 一、内容列表
- 1.Webservcie中的Schema文件约束理解
- 2.使用JDK支持实现WebService的服务端代码
- 3.使用JDK支持实现WebServcie的客户端代码，如何使用IDEA自动生成websercie-client
- 4.使用Appache CXF实现WebService的服务端和客户端，如何添加自定义客户端和服务端拦截器
- 5.使用Spring集成Appache CXF实现Webservice的客户端和服务端的实现

#### 二、注意要点
- 1.服务端注解@Webservice和@WebMethod可注解在接口上而不用注解在实现类上。
- 2.JDK编写的websercie代码不支持Map类型的返回参数而CXF支持。
- 3.JDK的请求saop和CXF的请求头会有不同
- 4.一般使用websercie的客户端拦截器和服务端拦截器做header信息处理。

#### 三、项目截图
- 1.WebService服务端代码
```java
@WebService(serviceName="SecondServiceName", portName="SecondPortName", name="SecondPortType", targetNamespace="http//:www.kiwipeach.cn/secondService")
public interface SecondWS {

    @WebMethod(operationName = "echoMsgMethod")
    @WebResult(name="echoResult")String echoMsg(@WebParam(name="paramMsg")String msg);
}
```
```xml
    <jaxws:endpoint id="serverEndpoint1"
            implementor="cn.kiwipeach.ws.api.impl.HelloWorldWSImpl"
            address="/firstWS">
         <jaxws:inInterceptors>
            <bean class="cn.kiwipeach.interceptor.CustomCXFServerInterceptor"></bean>
        </jaxws:inInterceptors>
    </jaxws:endpoint>
```

- 2.WebService的SpringWeb项目启动界面
##### 默认启动是跳转界面
![输入图片说明](https://gitee.com/uploads/images/2017/1114/173424_4dfc5b3d_1387578.png "02.png")
##### 查看http://192.168.1.107:8080/secondWS?wsdl内容
![输入图片说明](https://gitee.com/uploads/images/2017/1114/173658_997ea724_1387578.png "03.png")
##### 查看http://192.168.1.107:8080/secondWS?wsdl=SecondPortType.wsdl内容
![输入图片说明](https://gitee.com/uploads/images/2017/1114/173708_7331a85c_1387578.png "04.png")
- 3.WebService客户端调用代码
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml"})
public class SpringCXFClientTest {

    @Autowired
    private SecondPortType secondPortType;
    @Test
    public void testSecondWS(){
        System.out.println(secondPortType);
        String paramMsg = "KiWiPeach";
        String s = secondPortType.echoMsgMethod(paramMsg);
        System.out.println(s);
    }
}
```


