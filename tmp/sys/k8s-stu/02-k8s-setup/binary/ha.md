# kubernetes（k8s）二进制v13.1HA部署
[link](https://www.jianshu.com/p/65610d945ad7)

keepalive + haproxy + kube-apiservers  
kube-controller-manager  
kube-scheduler

docker  
kube-proxy  
kubelet

//节点规划  
10.8.13.80   vip  
10.8.13.81   master01  haproxy、keepalived、etcd、kube-apiserver、kube-controller-manager、kube-scheduler  
10.8.13.82   master02  haproxy、keepalived、etcd、kube-apiserver、kube-controller-manager、kube-scheduler  
10.8.13.83   master03  haproxy、keepalived、etcd、kube-apiserver、kube-controller-manager、kube-scheduler  
10.8.13.84   node01    kubelet、docker、kube_proxy、flanneld  
10.8.13.85   node02    kubelet、docker、kube_proxy、flanneld

# kubernetes（k8s）二进制v14.1HA部署
[link](https://www.jianshu.com/p/029f4d1d285d)  
ipvs + flanneld


#
https://www.cnblogs.com/kingle-study/p/10421290.html


https://www.cnblogs.com/along21/p/10044931.html