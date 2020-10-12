# CNI
https://www.cnblogs.com/sunsky303/p/11263675.html

https://github.com/AliyunContainerService/terway



```
You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/
```  

https://blog.csdn.net/bbwangj/article/details/82557516
#bgp
https://blog.csdn.net/weixin_34345560/article/details/89799585

[tcp/ip 3次握手4次挥手](https://www.cnblogs.com/kingle-study/p/9480689.html)

[子网划分](https://www.cnblogs.com/kingle-study/p/9492101.html)
https://blog.csdn.net/weixin_34345560/article/details/92670676



[单播、组播、广播](https://www.cnblogs.com/kingle-study/p/9473143.html)

[vxlan](https://www.jianshu.com/p/cccfb481d548)

https://www.cnblogs.com/sunsky303/p/12818024.html
https://www.cnblogs.com/sunsky303/p/12818009.html
https://www.cnblogs.com/wn1m/p/11290336.html

#k8s-coredns service通信，跨
```
ingcreations-service-promexport.default.svc.cluster.local
telnet  ingcreations-service-promexport.default 8888
Connected to ingcreations-service-promexport.default.svc.cluster.local.
```


# CNI calico替换flannel

##
```
[root@k8s-master ~]# kubectl -n kube-system get pods,daemonset,deployment
NAME                                           READY   STATUS             RESTARTS   AGE
pod/calico-kube-controllers-59757ccf85-4z2rh   1/1     Running            0          18d
pod/calico-node-fnjjh                          1/1     Running            0          5h47m
pod/calico-node-kd92w                          1/1     Running            0          5h47m
pod/coredns-94d74667-7vqpt                     1/1     Running            0          5h47m
pod/coredns-94d74667-fxzvd                     0/1     CrashLoopBackOff   83         5h47m
pod/etcd-k8s-master                            1/1     Running            0          18d
pod/kube-apiserver-k8s-master                  1/1     Running            0          18d
pod/kube-controller-manager-k8s-master         1/1     Running            0          18d
pod/kube-proxy-jzrf2                           1/1     Running            0          5h47m
pod/kube-proxy-rmtjk                           1/1     Running            0          5h47m
pod/kube-scheduler-k8s-master                  1/1     Running            0          18d
pod/tiller-deploy-5446588b7-np6sm              1/1     Running            0          12d

NAME                               DESIRED   CURRENT   READY   UP-TO-DATE   AVAILABLE   NODE SELECTOR                 AGE
daemonset.extensions/calico-node   2         2         2       2            2           kubernetes.io/os=linux        18d
daemonset.extensions/kube-proxy    2         2         2       2            2           beta.kubernetes.io/os=linux   18d

NAME                                            READY   UP-TO-DATE   AVAILABLE   AGE
deployment.extensions/calico-kube-controllers   1/1     1            1           18d
deployment.extensions/coredns                   1/2     2            1           18d
deployment.extensions/tiller-deploy             1/1     1            1           12d

```



```
\"ingresses\" in API group \"networking.k8s.io\" at the cluster scope"level=error ts=2020-07-03T13:56:13.677Z caller=klog.go:94 component=k8s_client_runtime func=ErrorDepth msg="/app/discovery/kubernetes/kubernetes.go:429: Fa
iled to list *v1beta1.Ingress: ingresses.networking.k8s.io is forbidden: User \"system:serviceaccount:prometheus-monitor:prometheus\" cannot list resource \"ingresses\" in API group \"networking.k8s.io\" at the cluster scope"
```





```
单工：简单的说就是一方只能发信息，另一方则只能收信息，通信是单向的。
半双工：比单工先进一点，就是双方都能发信息，但同一时间则只能一方发信息。
全双工：比半双工再先进一点，就是双方不仅都能发信息，而且能够同时发送。
```