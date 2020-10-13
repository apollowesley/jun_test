--编译请用 ant enc 命令，该命令会自动加密一些DSB server端核心类
--DSB client侧任何runtime的类均不能加密，因为DSB client侧不作任何class解密工作

1. DSB (Dynamic Service Beab) is a framework to manage it's dynamic service
   a dynamic service must have an interface to expose all it's functionalities, 
   which can be called remotely or locally by other services.
   Dynamic service started or initialized by DSB framework, this means the lifecyle
   of all dynamic service is controlled by DSB framework
2. Dynamic services must have one ore more deployment descriptors, each service must
   have one bean desriptor ( refer to BeanDescriptor.xsd), bean deployment descriptor is
   a collection of bean descriptor (refer to BeanDeployment.xsd), which contains one ore 
   more bean desriptor.
3. Dynamic service can have one or more i18n resource files(local i18n resource), 
   services deployed into one  bean descriptor(refer to BeanDeployment.xsd) can share 
   the same i18n resource(global i18n resource). those i18n resource can be retived by call:
   ServiceContext.getString(String key) 
4. Dynamic service can have one or more configuration files, those config files must place
	 into folder config/service/{serviceName}, and can be retrived by call 
	 ServiceContext.getConfigFile(String fileName)
5. Dynamic service also can have one or more resource files, those resource files must 
	 pace into folder resource/service/{serviceName}, and can be retrived by call
	 ServiceContext.getResourceFile(String fileName)
6. DSB (3.0) is only applicable for jre1.6 or higher version, while DSB (1.0) is only applicable for jre1.5 or lower version

7. compare to version 2.0, this version enable call forwarding as per service name or method name or method parameter value.

8. 改进了lookup机制（针对整个service的转发机制, 为了提高效率，不能根据方法名以及参数来转发）

9. 改进了service启动机制(dsb-conf.xml新增startupservices, 因为有些service必须优先启动, 系统启动时会忽略service的startup属性而强制启动)

10.改进了service启动机制(dsb-conf.xml新增disabledservices,因为有些service只是作为调用引入的包而无需启动, 系统启动时会忽略service的startup属性而不启动)

11.新增了查看service启动状态/启动(start)/关闭(close)/刷新(refresh)接口

3.2 新功能 (2014-12-7)
12) HA 特性: 在ServiceFactory新增方法：
    <T extends DynamicService> T lookup_ha(String serviceName, String haName)
    ServiceProxy lookup_ha(String haName) throws Exception;
          实现方法调用的高可用性high availability (HA)
          在调用上述方法前，需要在system properties中设置HA name，例如System.setProperty("dsb_ha_1", "localhost:20000,localhost:10000");
    IP地址和端口之间通过分号"："连接，多个配置项目用逗号","分割,此项配置支持1~n个配置项，这意味着HA集群中可支持n个DSB服务热备，实际情况下，2个热备足够使用 
    
13) 3.2版本中新增HA特性后， service forward功能暂不可用，因为forward有递归实现方式，存在一定风险

14) 在ServiceContext接口中新增两个方法：
    <T extends DynamicService> T lookup_ha(String serviceName, String haName) throws Exception;
    ServiceProxy lookup() throws Exception;
    
3.2 特性说明(2015-03-21)
15) 3.2版本中HA特性依然不具备商用价值，因为serviceProxy.ping()方法在服务器网络断开或者应用关闭时会长时间阻塞(windows下通常为21秒，mac os下为128秒)
     原因在于RMISocketFactory底层socket connect的超时时间为0(意味着无限期)，超时时间则由操作系统底层决定，这个问题需要反映给oralce(sun)公司，应该能够显式设置timeout
     
16) 3.2版本中HA是用来研究和学习的，除HA功能外，其他功能均稳定正常，在dsb-4.0中引入高性能mina框架后，把HA特性提升到商用水平

17) 特别注意的是，ant enc命令编译打包时，client侧任何runtime的类均不能加密，因为DSB client侧不作任何class解密工作

18) 架构改进: 3.6版本中将进入注册中心，服务端启动后向注册中心注册它所能够提供的服务

19) 3.5版本中去除DSB-Forward特性  (2015-04-12)

20) 3.5版本中将增加Annotation特性 (DBSession annotation)，同时集成MyBatis  (2015-04-12)

21) 3.5版本中对main代码进行了重构，部分类调整了package,因此3.2升级到3.5后，不兼容，需要修改代码，涉及的类为ServiceContext (2015-04-12)

22) 3.5版本中把command-linker-3.0集成到framework中，通过DSBcmd Annotation来初始化CommandHandler，省去配置文件,同时去除ConsoleService

--最后更新时间为 2015-05-01
--jdk version: 1.6.0_45