# grafana
https://grafana.com/grafana/  
https://grafana.com/docs/  
https://grafana.com/grafana/dashboards  //dashboard templates  
https://grafana.com/docs/grafana/latest/  
https://github.com/grafana/grafana  

```
Grafana是一个可视化仪表盘，它拥有美观的图标和布局展示，功能齐全的仪表盘和图形编辑器，默认支持 CloudWatch、Graphite、Elasticsearch、InfluxDB、Mysql、PostgreSQL、Prometheus、OpenTSDB 等作为时序数据源。我们可以将 Prometheus 抓取的数据，通过 Grafana 优美的展示出来，非常直观。
```
特点：
- go编写轻量级
- 指标可视化展示
- 流行的时序数据展示

# setup
//docker
https://hub.docker.com/r/grafana/grafana
>home:/usr/share/grafana

```
docker run -d --name=grafana -p 3000:3000 grafana/grafana
http://localhost:3000 admin/admin

docker run -d -p 3000:3000 -e TZ="Asia/Shanghai" --link=prometheus -v /data/grafana:/var/lib/grafana -v /data/grafana.ini:/etc/grafana/grafana.ini --name=grafana  grafana/grafana:7.0.3
```

# grafana详细配置
https://www.jianshu.com/p/7e7e0d06709b
https://blog.csdn.net/bbwangj/article/details/81109615
https://blog.csdn.net/boonya/article/details/89473132


## grafana 监控模板
https://www.jianshu.com/p/367d52fe1171

https://www.jianshu.com/p/ebba4617371c
https://www.jianshu.com/p/401bd7b3c25b