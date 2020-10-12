# flanael
https://github.com/coreos/flannel


kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

https://blog.csdn.net/weixin_41806245/article/details/102818155
https://blog.csdn.net/bbwangj/article/details/81092728
https://blog.csdn.net/bbwangj/article/details/82669226


# 清除 flannel 网络
```
ifconfig cni0 down
ifconfig flannel.1 down
ifconfig del flannel.1
ifconfig del cni0

ip link del flannel.1
ip link del cni0

如果没有 brctl 命令
yum install bridge-utils

brctl delbr  flannel.1
brctl delbr cni0

rm -rf /var/lib/cni/flannel/* && rm -rf /var/lib/cni/networks/cbr0/* && ip link delete cni0 &&  rm -rf /var/lib/cni/network/cni0/*
```