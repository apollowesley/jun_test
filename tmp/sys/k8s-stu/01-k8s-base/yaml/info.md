deployment
service

k8s上的Service是抽象的，其定义了一组运行在集群之上的Pod的逻辑集合，这些Pod是重复的，复制出来的，所以提供相同的功能。当Service被创建，会被分配一个唯一的IP地址（也称为集群IP）。这个地址和Service的生命周期相关联，并且当Service是运行的时候，这个IP不会发生改变。Pods进行配置来和这个Service进行交互，之后Service将会自动做负载均衡到Service中的Pod。

port字段是指抽象Service的端口
targetPort字段是指容器内开放的端口
nodePort为节点上暴露的端口号，不指定的话为随机

//标签选择器 -l
kubectl get pods -l app=nginx -o wide

//查看pod的ip
kubectl get pods -l app=nginx -o json | grep podIP


```
[root@c-3-102 ~]# kubectl describe svc ngx-svc
Name:              ngx-svc
Namespace:         default
Labels:            <none>
Annotations:       <none>
Selector:          app=nginx
Type:              ClusterIP
IP:                10.20.153.145
Port:              http  8080/TCP
TargetPort:        80/TCP
Endpoints:         10.10.110.134:80  //pod节点信息
Session Affinity:  None
Events:            <none>

[root@c-3-102 ~]# kubectl get ep   //endpoint
NAME         ENDPOINTS            AGE
kubernetes   192.168.3.102:6443   9h
ngx-svc      10.10.110.134:80     9m51s

```


pod服务暴露到外部
NodePorts和LoadBalancers