# niki-redis 是nikijava的又一个中间件

#### 介绍
niki-redis是基于spring+maven+jedis 封装而成的一套redis中间件服务，采用拔插式设计，对接非常简单，配好redis地址端口直接就可以在项目中轻松使用redis，还能支持个性化设计。

#### 软件架构
软件架构说明：redis数据库是很多java项目中常用的一个nosql缓存数据库，niki-redis是基于jedis为根基打造出的一款非常好用，对接简单的中间件！


#### 安装教程

1. 先在你的application.yml或者application.xml文件中配置redis地址和ip，key保持跟我这下面一样，没有密码可以不写，有密码就要写：
		spring.redis.host: 192.168.3.13
		spring.redis.port: 6379
		spring.redis.password:
		spring.redis.timeout: 60000
2. 增加spring包扫描@ComponentScan(value = {"com.niki.redis", "xxx.xxx.xxx"}), com.niki.redis这个是我的redis包路径，必须加入spring容器管理
3. 完成以上2步以后你只需要注入  @Autowired    RedisUtils redisUtils; 这个工具类就可以愉快的使用了
4，是不是非常简单，redisUtils目前只支持一些常用的，如果redis里面没有的，你可以自己去redisutils 里面加，或者你自己弄一个类继承一下redisUtils。
redisUtils它的父类CacheManager 基本啥方法都有，所有数据类型的操作都在CacheManager中。redisutils中间增加key的前缀，大家注意一下，
如果你继承redisutils最好使用redisutils里面的前缀，如果你重新弄一个前缀也可以！

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
