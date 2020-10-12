
[Prometheus+Grafana监控Kubernetes](https://blog.csdn.net/shenhonglei1234/article/details/80503353)

#资源文件
```
https://pan.baidu.com/s/18XHK7ex_J0rzTtfW-QA2eA 密码:0qn6
```

#
```
[root@c-3-103 ~]# kubectl get ns
NAME                   STATUS   AGE
default                Active   6h
kube-public            Active   6h
kube-system            Active   6h
kubernetes-dashboard   Active   4h51m
ns-monitor             Active   7m41s
[root@c-3-103 ~]# kubectl -n ns-monitor get pods,svc
NAME                              READY   STATUS    RESTARTS   AGE
pod/grafana-54bd9cb559-6h7kb      1/1     Running   0          7m43s
pod/node-exporter-cb8cg           1/1     Running   0          7m43s
pod/node-exporter-hnwv2           1/1     Running   0          7m43s
pod/prometheus-7954895968-s9jhw   1/1     Running   0          3m33s

NAME                            TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
service/grafana-service         NodePort   10.20.196.242   <none>        3000:30677/TCP   7m43s
service/node-exporter-service   NodePort   10.20.126.82    <none>        9100:31672/TCP   7m43s
service/prometheus-service      NodePort   10.20.157.183   <none>        9090:30872/TCP   3m32s
[root@c-3-103 ~]# 
```

- grafana       3000   //展示数据、告警、存储
- prometheus    9090   //采集汇总
- node-exporter 9100   //采集node


#数据上报接口
/metrics

curl http://127.0.0.1:9100/metrics



#grafana
>用户名和密码：admin/admin
admin/admin123456