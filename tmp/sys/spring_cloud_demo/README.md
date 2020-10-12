### spring_cloud_demo

spring cloud经典学习案例大全

### 作者及博客

QQ：949118693（邪客）

Java技术交流群：780509097

![输入图片说明](https://gitee.com/uploads/images/2018/0616/091012_2e93400f_583593.png "Java技术交流群.png")

博客：http://xieke90.iteye.com/

### 项目结构说明

 **1.spring_cloud_eureka_demo：eureka服务治理案例** 
- eureka的服务注册中心：eureka-server
- eureka的服务提供方：eureka-client
- eureka的服务消费者：eureka-consumer
- 将eureka-server、eureka-client、eureka-consumer都启动起来，然后访问http://localhost:2101/consumer 观察效果。

 **2.eureka-consumer-ribbon：基于上述案例提供负载均衡** 
- 将eureka-server、eureka-client、eureka-consumer-ribbon启动起来；
- 访问http://localhost:2101/consumer 观察效果；
- 通过启动多个eureka-client服务来观察其负载均衡的效果；

 **3.eureka-consumer-feign：声明式服务调用客户端** 
- 将eureka-server、eureka-client、eureka-consumer-feign启动起来；
- 访问http://localhost:2101/consumer 观察效果；
- 可以通过启动多个eureka-client服务来观察其负载均衡的效果；

### 后续持续更新...
