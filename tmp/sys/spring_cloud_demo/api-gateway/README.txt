1、先后启动eureka-server,eureka-client,eureka-consumer,api-gateway;
2、Zuul默认创建下面的两个路由规则：
转发到eureka-client服务的请求规则为：/eureka-client/**
转发到eureka-consumer服务的请求规则为：/eureka-consumer/**
3、访问http://localhost:1101/eureka-client/dc ,该请求将最终被路由到eureka-client的/dc接口;
4、访问http://localhost:1101/eureka-consumer/consumer ,该请求将最终被路由到eureka-consumer的/consumer接口;
5、过滤器部分：
http://localhost:1101/eureka-client/dc：返回401错误
http://localhost:1101/eureka-client/dc?accessToken=token：正确路由到eureka-client的/dc接口;