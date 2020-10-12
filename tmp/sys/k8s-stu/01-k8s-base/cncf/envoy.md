# envoy
https://github.com/envoyproxy/envoy  
https://www.envoyproxy.io/  
https://www.envoyproxy.io/docs/envoy/latest/start/start

##envoy zh_cn  
https://www.servicemesher.com/envoy/


## reference
[envoy云原生](https://www.servicemesher.com/blog/thoughts-to-envoy-from-adn-perspective/)

```
ENVOY IS AN OPEN SOURCE EDGE AND SERVICE PROXY, DESIGNED FOR CLOUD-NATIVE APPLICATIONS Envoy 是一个开源的边缘以及服务代理，为云原生应用而生
```

```
Envoy 简介
Envoy 是专为大型现代 SOA（面向服务架构）架构设计的 L7 代理和通信总线，经常被用在 Service Mesh 中作为 Sidecar。它具有以下优点：
非侵入的架构：Envoy 是和应用服务并行运行的，透明地代理应用服务发出/接收的流量。应用服务只需要和 Envoy 通信，无需知道其他微服务应用在哪里。
基于 Modern C++11实现，性能优异。
L3/L4 过滤器架构：Envoy 的核心是一个 L3/L4 代理，然后通过插件式的过滤器(network filters)链条来执行 TCP/UDP 的相关任务，例如 TCP 转发，TLS 认证等工作。
HTTP L7 过滤器架构：HTTP在现代应用体系里是地位非常特殊的应用层协议，所以 Envoy 内置了一个非常核心的过滤器: http_connection_manager。http_connection_manager 本身是如此特殊和复杂，支持丰富的配置，以及本身也是过滤器架构，可以通过一系列 http 过滤器(http filters)来实现 http协议层面的任务，例如：http路由，重定向，CORS支持等等。
HTTP/2 作为第一公民：Envoy 支持 HTTP/1.1 和 HTTP/2，推荐使用 HTTP/2。
gRPC 支持：因为对 HTTP/2 的良好支持，Envoy 可以方便的支持 gRPC，特别是在负载和代理上。
服务发现： 支持包括 DNS, EDS 在内的多种服务发现方案。
健康检查：内置健康检查子系统。
高级的负载均衡方案：除了一般的负载均衡，Envoy 还支持基于 rate limit 服务的多种高级负载均衡方案，包括： automatic retries, circuit breaking, global rate limiting
Tracing：方便集成 Open Tracing 系统，追踪请求
统计与监控：内置 stats 模块，方便集成诸如 prometheus/statsd 等监控方案
动态配置：通过“动态配置API”实现配置的动态调整，而无需重启 Envoy 服务的
```

https://cloud.tencent.com/developer/information/Envoy
https://www.jianshu.com/p/90f9ee98ce70
https://zhuanlan.zhihu.com/p/87441584
https://blog.csdn.net/lijiaocn/article/details/100638831

## envoy 前段代理

##docker-envoy
https://www.envoyproxy.io/docs/envoy/latest/install/building

https://hub.docker.com/r/envoyproxy/envoy-alpine/tags
docker pull envoyproxy/envoy-alpine

docker run --rm -it envoyproxy/envoy-alpine ls -l /etc/envoy
>默认配置文件 /etc/envoy/envoy.yml

##envoy_config.json
https://gist.githubusercontent.com/jvns/340e4d20c83b16576c02efc08487ed54/raw/1ddc3038ed11c31ddc70be038fd23dddfa13f5d3/envoy_config.json

```
{"static_resources": {
  "listeners": [
    {
      "address": { "socket_address": { "address": "0.0.0.0", "port_value": 7777 } },
      "filter_chains": {
        "filters": [
          {
            "name": "envoy.http_connection_manager",
            "config": {
              "stat_prefix": "ingress_http",
              "http_filters": [{
                "name": "envoy.router",
                "config": {}
              }],
              "route_config": {
                "virtual_hosts": [
                  {
                    "name": "blah",
                    "domains": "*",
                    "routes": [
                      {
                        "route": {
                          "cluster": "banana"
                        },
                        "match": {
                          "prefix": "/"
    }}]}]}}}]}}],
  "clusters":[
    {
      "name": "banana",
      "type": "STRICT_DNS",
      "connect_timeout": "1s",
      "hosts": [
        { "socket_address": { "address": "192.168.3.100", "port_value": 8080 } }
      ]
    }
  ]
}}
```

##backend server
```
cd /tmp/ && mkdir sb && cd sb && echo envoy >>index.html

//3
python -m http.server 8080

//2
python -m SimpleHTTPServer  8080
```

##proxy
```
docker run --rm --net host -v=$PWD:/config envoyproxy/envoy-alpine  /usr/local/bin/envoy -c /config/envoy_config.json

docker run --rm  -p 7777:7777 -v=$PWD:/config envoyproxy/envoy-alpine  /usr/local/bin/envoy -c /config/envoy_config.json
```
```
curl -v localhost:7777
```
[link](https://www.jdon.com/50539)