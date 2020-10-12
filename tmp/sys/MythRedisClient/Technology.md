# 关于技术实现的细节解释
- 自定义的异常是实例化的时候可以自定义的记录日志,而且自定义异常提示信息，方便弹窗提示等便利的提示

### 关于 redis_core 模块的初始化
- 使用PoolManagemen的单例获取实例，设置下配置文件中的id就可用了
- 所以在进行配置文件的操作的时候，打开的是没有连接池实例的连接池管理对象
- 切换连接池，通过 switchPool方法来实现（不能直接set，如果前台进行了限制倒也可以），因为都是动态获取Jedis连接的

 
