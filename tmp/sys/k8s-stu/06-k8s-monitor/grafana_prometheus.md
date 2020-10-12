# 组合
- prometheus做监控
- grafana做dashboard的界面

https://o-my-chenjian.com/2018/01/27/Deploy-Prometheus-And-Grafana-On-Kubernetes/
https://blog.51cto.com/kaliarch/2160569

[link](https://blog.csdn.net/jerryhu1234/article/details/86476939)

https://blog.csdn.net/weixin_41806245/article/details/93970743
https://www.cnblogs.com/sunsky303/p/11276349.html
https://blog.csdn.net/bbwangj/article/details/82563626

[prometheus监控](https://blog.csdn.net/jerryhu1234/article/details/81737812)

#prometheus 主程序
https://github.com/prometheus/prometheus/releases/download/v2.3.2/prometheus-2.3.2.linux-amd64.tar.gz

#prometheus 告警
https://github.com/prometheus/alertmanager/releases/download/v0.15.2/alertmanager-0.15.2.linux-amd64.tar.gz

#node-exporter 
https://github.com/prometheus/node_exporter/releases/download/v0.16.0/node_exporter-0.16.0.linux-amd64.tar.gz

#mysql-exporter
https://github.com/prometheus/mysqld_exporter/releases/download/v0.11.0/mysqld_exporter-0.11.0.linux-amd64.tar.gz

#my.cnf
```
[client]
host = 127.0.0.1
user = REPL
password = Pbu4@123
socket = /mysqldata/data/mysqld.sock
[mysqladmin]
host = 127.0.0.1
user = zabbix
password = Zabbix@123
socket = /mysqldata/data/mysqld.sock

nohup ./mysqld_exporter --config.my-cnf=/etc/zabbix/.my.cnf --collect.binlog_size --collect.info_schema.processlist --collect.info_schema.innodb_metrics --collect.engine_innodb_status --collect.perf_schema.file_events --collect.perf_schema.eventswaits --collect.perf_schema.indexiowaits --collect.perf_schema.tableiowaits --collect.info_schema.tables --collect.info_schema.tablestats --collect.auto_increment.columns --collect.info_schema.userstats --collect.info_schema.innodb_cmp --collect.info_schema.innodb_cmpmem --collect.perf_schema.replication_group_member_stats >/dev/null &
```


#start prometheus
nohup ./prometheus >/dev/null &
nohup ./alertmanager >/dev/null &
nohup ./node_exporter >/dev/null &








#recond
[link](https://www.digitalocean.com/community/tutorials/how-to-set-up-a-kubernetes-monitoring-stack-with-prometheus-grafana-and-alertmanager-on-digitalocean)

https://github.com/do-community/doks-monitoring/tree/master/manifest



## exporter
rabbitmq-exporter



##rabbitmq加入监控
[link](https://www.kubernetes.org.cn/7130.html)
```
$ helm install prom-rabbit stable/prometheus-rabbitmq-exporter \
    --set "rabbitmq.url=http://rabbit-service:15672" \
    --set "rabbitmq.user=user" --s set "rabbitmq.password=bitnami" --namespace=kube-public
```