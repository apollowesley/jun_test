#btg-session-jfinal
```
注意：由于该项目为独立的session管理组件，所以在项目使用时，请使用本组件API，Servlet的API将不受此组件支持，
若在Servlet环境使用，推荐使用另一个版本[btg-httpsession-jfinal](http://git.oschina.net/usbtg/btg-httpsession-jfinal)

===========================================================================================================
v2.1.1：
1、修复项目重启后session清空的bug；

v2.0.1703291407：
1、防止更新太频繁影响性能，增加session最后访问时间更细同步机制多重策略配置，内置三种方式，
   实时(设置为0)，间隔时间(默认，30000毫秒)，不更新(设置为小于0)；
2、调整session最后访问时间更新算法，最后访问时间大于上次访问时间时更新；
3、BTGSession增加克隆方法；
4、调整session刷新更新算法，保留原有session参数(重复时使用新值覆盖旧值的方式处理)；

v2.0.1：
1、使用btg-util工具类替换项目工具类；
2、基于btg-httpsession-jfinal项目对此项目进行重构，重构后特性与httpsession一致；
3、API优化；
4、性能提升；
5、过期session清理算法机制调整并优化；

v1.0.1：
1、基于jfinal。友好的api与可配置式插件，高性能，站在巨人的肩膀上；
2、应用层Session管理，不依赖servlet容器。完全独立的session管理机制；
3、支持分布式session管理。已实现基于redis、db；
4、支持session二级缓存。针对于db、tfp文件系统等低并发低性能session存储介质，启用二级缓存，可减少存储访问，提高性能；
5、企业级session管理。session过期自动清理，及时释放资源，保证高并发下可用性；
6、面向接口。可随意编写自定义包括最核心的session manager的扩展。
7、提供jfinal插件。直接在jfinal插件配置中添加配置；
8、静态访问接口。可直接通过SessionKit访问全套session API 告别繁琐的代码；
9、免费技术支持与不断更新完善。
10、apache开源协议，人人皆用户，人人皆作者。

===========================================================================================================

一、配置方式：
    JfinalConfig：用户初始化sessionDao实现方案和session管理上下文
        public void configPlugin(Plugins me) {
            //local(第二句为默认，可不配置)
            BTGSessionDao localSessionDao = new BTGLocalSessionDao();
            BTGSessionPlugin sessionPlugin = new BTGSessionPlugin(localSessionDao);
            me.add(sessionPlugin);

            //redis
            BTGSessionDao redisSessionDao = new BTGRedisSessionDao();
            BTGSessionDao redisSessionDao = new BTGRedisSessionDao("myredis");
            BTGSessionPlugin sessionPlugin = new BTGSessionPlugin(redisSessionDao);
            me.add(sessionPlugin);

            //db
            BTGSessionDao dbSessionDao = new BTGDBSessionDao();
            BTGSessionDao dbSessionDao = new BTGDBSessionDao("mydb");
            BTGSessionPlugin sessionPlugin = new BTGSessionPlugin(dbSessionDao);
            me.add(sessionPlugin);
        }

二、调用API：
1、使用BTGHttpSessionContext接口的实现类BTGStandardSessionContext直接完成对session的操作；
    getSession(String sessionId):BTGStandardSessionContext.getSessionContext().getSession(sessionId)
    getNewSession():BTGStandardSessionContext.getSessionContext().getNewSession()
    更多api请查看BTGSessionContext接口

2、(推荐)使用静态访问类SessionKit完成对session的操作；
    getSession(String sessionId):SessionKit.getSession(sessionId)
    getNewSession():SessionKit.getNewSession()
    更多api请查看SessionKit类
===========================================================================================================
```
豆圆