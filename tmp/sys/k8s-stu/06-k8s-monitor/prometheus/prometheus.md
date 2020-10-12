# prometheus
https://prometheus.io/
https://prometheus.io/docs/prometheus/latest/configuration/configuration/
https://github.com/prometheus/prometheus
https://prometheus.io/download/
https://hub.docker.com/r/prom/prometheus/

```
Prometheus是一套开源的系统监控、报警、时间序列数据库的组合，Prometheus 基本原理是通过 Http协议周期性抓取被监控组件的状态，而通过Exporter Http接口输出这些被监控的组件信息，而且已经有很多 Exporter可供选择。

prometheus 当前已经成为k8s的主流监控方案。具备集群资源监控、服务监控、告警与一体，具备较强的扩展性与集成能力
扩展性：可以通过多个prometheus 采集多个不同区域的数据，使用联邦集群技术在一个prometheus上进行汇聚与展示
集成能力：目前支持： Java， JMX， Python， Go，Ruby， .Net， Node.js等等语言的客户端SDK，基于这些SDK可以快速让应用程序纳入到Prometheus的监控当中，或者开发自己的监控数据收集程序。
```

##k8s的prometheus并不单单只有这一组件
```
- export: Exporter将监控数据采集的端点通过HTTP服务的形式暴露给Prometheus Server，Prometheus Server通过访问该Exporter提供的Endpoint端点，即可获取到需要采集的监控数据

- prometheus-server：负责实现对监控数据的获取，存储以及查询。 Prometheus Server可以通过静态配置管理监控目标，也可以配合使用Service Discovery的方式动态管理监控目标，并从这些监控目标中获取数据。自带web ui，可以直接通过PromQL实现数据的查询以及可视化
  
- grafana：展示面板，将prometheus中的数据通过面板图形化更友好的方式展示

- alertmanager: Prometheus根据PromQL定义的规则，产生一条告警，告警的后续处理流程由AlertManager进行管理。在AlertManager中我们可以与邮件，Slack等等内置的通知方式进行集成，也可以通过Webhook自定义告警处理方式。AlertManager即Prometheus体系中的告警处理中心
```

## prometheus 架构
https://prometheus.io/docs/introduction/overview/
![prometheus_archit](https://upload-images.jianshu.io/upload_images/6966622-9fce3511e88287bf.png?imageMogr2/auto-orient/strip|imageView2/2/w/1178/format/webp)

https://www.cnblogs.com/jayce9102/p/12968874.html

https://blog.csdn.net/bbwangj/article/details/82658608
https://blog.csdn.net/bbwangj/article/details/81056877
https://blog.csdn.net/bbwangj/article/details/88973093
https://blog.csdn.net/bbwangj/article/details/81978002
https://blog.csdn.net/bbwangj/article/details/82658419
https://blog.csdn.net/bbwangj/article/details/82832883
https://blog.csdn.net/wangshuminjava/article/details/90521666
https://blog.csdn.net/bbwangj/article/details/88963125


# setup
//docker
```
docker run -d --name=prometheus -p 9090:9090 \
  -v /$PWD/prometheus.yml:/etc/prometheus/prometheus.yml \
  --config.file=/etc/prometheus/prometheus.yml prom/prometheus

```


[k8s-prometheus](https://blog.csdn.net/shenhonglei1234/article/details/85275183)
https://blog.csdn.net/boonya/article/details/105295473
https://blog.csdn.net/boonya/article/details/105273043
https://blog.csdn.net/boonya/article/details/105272952





curl 10.10.2.4:9090/healthy
/metrics

```
services:
  prometheus:
    image: prom/prometheus:v2.19.2
    networks:
      - net
    volumes:
      - /mnt/media/prometheus_data:/prometheus
      - ./prometheus:/etc/prometheus
      - /etc/timezone:/etc/timezone
    ports:
      - 9090:9090
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
      - '--storage.tsdb.path=/prometheus'
      - '--storage.tsdb.retention.time=90d'
      - '--web.console.libraries=/etc/prometheus/console_libraries'
      - '--web.console.templates=/etc/prometheus/consoles'
      - '--web.enable-lifecycle'
```


https://github.com/grafana/kubernetes-app
# get metrics
1)kube-state-metrics插件
2)node-exporter插件
3)prometheus插件
4)black-box-exporter插件
5)alertmanager插件
6)prometheus-webhook-dingtalk插件


## alerting
https://www.jianshu.com/p/0224ba6327b3
https://www.jianshu.com/p/302e1291e929