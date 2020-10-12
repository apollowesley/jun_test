1、启动命令
redis-server redis.windows.conf


2、设置服务命令

redis-server --service-install redis.windows-service.conf --loglevel verbose

3、常用的redis服务命令。

卸载服务：redis-server --service-uninstall

开启服务：redis-server --service-start

停止服务：redis-server --service-stop




Spring中的缓存
 

Spring从3.1版本开始就引入了基于注解的缓存支持，到现在已经发展的相当稳定了。Spring主要提供的是基于JSR107的抽象，对于缓存的具体实现可以是EhCache也可以是Redis。下面简单搬运一下几种注解的定义：
@Cacheable  缓存的入口，首先检查缓存如果没有命中则执行方法并将方法结果缓存
@CacheEvict  缓存回收，清空对应的缓存数据
@CachePut   缓存更新，执行方法并将方法执行结果更新到缓存中
@Caching    组合多个缓存操作
@CacheConfig 类级别的公共配置