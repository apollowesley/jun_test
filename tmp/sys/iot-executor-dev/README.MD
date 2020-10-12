### 执行器dev

>executor 一句话总结：允许设备在服务器定义一段代码，设备通过远程调用的方式获取执行结果


#### Groovy语言

Groovy语言也是属于动态语言，正如我们熟知的python。 Groovy是运行在JVM（JAVA 虚拟机）上的。iot_open 整个均是由Java编写，所以使用Groovy，服务器可以无缝的与脚本交互。 例如 可以将缓存组建，邮件组建。。 无缝传入传入到脚本里面。无需做任何序列化和反序列化。


Groovy语言完全兼容Java语法，所以在你不了解Groovy时。你完全可以使用Java的语法来编写执行器。


#### 脚本编写约定

	public Long getGmt(InvokeContext invokeContext){
        	return System.currentTimeMillis() / 1000;
    	}
    	
 上面是方法定义必须的方法签名。传入一个InvokeContext即可。
 
 invokeContext 顾名思义，就是此次调用的大管家。所有的资源都问invokeContext去要。他会封装一些常用的东西。下面我们来看下它的结构。
 
 	public class InvokeContext implements Serializable {

		private InvokerInfoModel invokerInfoModel;

		private ICache cache;

		private IMailSender mailSender;

		private OkHttpClient okHttpClient;

		private IDeviceLogger deviceLogger;
		
以上是目前为止所给的资源，以后会越来越多，所以这里不一定是最新的（请以代码为准）

invokerInfoModel : 即调用者的基本信息，例如IP等基本信息，具体请查看代码

OkhttpClient : 用户调用其他HttpApi

ICache : 缓存接口

IMailSender : 邮件发送接口

IDeviceLogger : 设备日志接口

这里约定I开头的都是接口，iot-executor-dev 只会给一个mock实现。在线上的环境中，将会给真实的实现实例。具体实现请查看 iot-executor 相关代码。

通过调用这些接口的接口方法，可以在线上环境完成一些事情。

例如当设备数据出现严重异常，比如设备温度超过100摄氏度，你可以通过mailSender向自己的邮箱发一封邮件。就完成了异常告警。

当然这里只是举一个例子，其他接口功能在接口声明出已经写了相应注释，请查看注释以了解功能