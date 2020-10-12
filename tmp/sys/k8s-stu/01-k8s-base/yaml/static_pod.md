## 静态pod

静态Pod，比较特殊，并没有被存放在kubernetes的etcd存储里，而是被存放在某个具体的Node上的一个具体文件中，并且只在此Node上启动、运行。

```
[root@c-3-104 ~]# ll /etc/kubernetes/manifests/
total 16
-rw------- 1 root root 1881 May 27 13:15 etcd.yaml
-rw------- 1 root root 2738 May 27 13:15 kube-apiserver.yaml
-rw------- 1 root root 2593 May 27 13:15 kube-controller-manager.yaml
-rw------- 1 root root 1149 May 27 13:15 kube-scheduler.yaml
```
