

### 特点
1. 基于akka 高性能分布式框架
2. 使用 spring 配置请求参数
3. 自动管理代理地址Ip，http请求重试， 超过重复次数丢弃请求
4. 针对任务请求，任务响应实现过滤(需要根据自己业务实现过滤逻辑)
5. 配置多数据源存储 抓取数据自由选择入库


### 系统核心组件
1. task  生成请求任务,填写 请求的url ，页面编码，header ，parma 参数
2. actor 具体抓取actor，处理taskRequest 的请求转发，页面分页，taskResponse 过滤
3. parse 解析具体页面内容，写库操作。

### 历史版本 
v 1.0.2
 1 修复 HttpRequestMeta 克隆参数的bug。 