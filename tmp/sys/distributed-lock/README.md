# distributed-lock

#### 项目介绍
A：分布式锁

B：防重复提交

一个通过redis 或 zookeeper等集中式存储而实现的弱一致性分布式锁；以及在此基础上实现的防重复提交功能；

通过@EnableDistributedLock 注解开启分布式锁

通过@EnableResubmit 注解开启防重复提交功能

目前暂时只支持redis; 后期可扩展至zookeeper; 


弱一致性：依赖redis服务器的稳定性，如果加锁成功，对应reids服务器down掉，又重启，就有可能导致锁失效；

####  使用范围
目前只支持spring boot项目


#### 软件架构



#### 安装教程

1. 下载源代码
2. 通过maven引入依赖jar包


#### 使用说明

0. 依赖

  项目引用redis，并创建redis对应的bean：RedisTemplate或JedisCluster 任一bean即可

1. 分布式锁

A: SpringBoot main class中引入注解：@EnableDistributedLock
```
@SpringBootApplication 
@EnableDistributedLock //启动分布式锁(前提：项目中已经存在RedisTemplate或JedisCluster对应的bean)
public class App
{
    public static void main( String[] args )
    {
        //启动项目
        SpringApplication.run(App.class,args);
    }
}
```

B: 业务代码
   ```
    //业务bean中引用lockManager
    @Autowired
    LockManager lockManager;
    
    
    //业务代码中使用lock
    DistributedLock lock = lockManager.getLock();
    try{
        boolean locked = lock.tryLock(key,50);
        if(locked){
            //... 业务

        }
    }finaly{
        boolean unlocked = lock.unlock();
    }
   ```

2. 防重复提交

    A: SpringBoot main class中引入注解：@EnableResubmit
    ```
      @SpringBootApplication
      @EnableResubmit
      public class App
      {
         public static void main( String[] args )
         {
            //启动项目
            SpringApplication.run(App.class,args);
         }
      }
    ```
    B: 业务代码

    业务方法上，使用@Resubmit注解；lockey：可以通过 spEL表达式获取方法参数中的值做为锁；
    expireSeconds 为锁过期时间，默认5秒；
    ```
    @Resubmit(lockPrefix = "resubmit:pre:" ,lockKey = "#name",expireSeconds = 5)
    public String resubmit(String name){
        return "name";
    }
    ```

   C：重复提交处理
     aop拦截到重复提交后，通过ResubmitResolver进行处理；
     默认会创建三个Resolver bean:
     - ExceptionResolver
       发现重复提交，直接抛出异常
     - HttpStatusResolver
       http请求：发现重复提交，返回423 http状态码；
     - PrintResolver；
       发现重复提交，仅仅打印日志；仅为测试，无业务意义；

     默认使用的Resolver,可能通过@EnableResubmit(defaultResolver = PrintResolver.class)指定（前提：指定的Resolver 的bean必须提前存在）
    
     自定义Resolver:
     实际业务中，除了使用统一的默认Resolver外，部分业务可能需要特殊的Resovler，此时可以由业务方开发自己的，实现了ResubmitResolver接口的Resolver；
     - 配置业务方自己实现的Resolver为spring Bean
     - 通过注解@Resubmit中的resolverBeanName 指定业务方自己的Resolver bean name
     ```
      @Resubmit(lockKey = "#name",resolverBeanName = "exceptionResolver") 
     ```

#### 参与贡献

