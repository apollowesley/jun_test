# docker-network
https://andrewpqc.github.io/2017/09/20/Network-management-of-Docker/
https://blog.csdn.net/bbwangj/article/details/81205244

https://blog.csdn.net/wangshuminjava/article/details/89913701
https://blog.csdn.net/bbwangj/article/details/81116145
# docker0 bridge
https://developer.ibm.com/recipes/tutorials/networking-your-docker-containers-using-docker0-bridge/

默认docker0 172.17.0.0/16 or 192.168.0.0/16,非默认网桥可以指定容器的IP
```
[root@tag home]#  systemctl stop docker
[root@tag home]#  iptables -t nat -F POSTROUTING            #查看一下防火墙的nat
[root@tag home]#  ip link set dev docker0 down              
[root@tag home]#  ip addr del 172.17.0.1/16 dev docker0     #删除docker默认网关配置

[root@tag home]#  yum install -y bridge-utils               
[root@tag home]#  brctl addbr bridge1                       #新建一张网卡
[root@tag home]#  ip addr add 172.18.2.1/24 dev bridge1     #增加新的docker网关配置
[root@tag home]#  ip link set dev bridge1 up
[root@tag home]#  ip addr show bridge1

[root@tag home]# cat > /etc/docker/daemon.json <<EOF
{
  "bridge": "bridge1"
}
EOF

[root@tag home]#  systemctl daemon-reload
[root@tag home]#  systemctl enable docker 
[root@tag home]#  systemctl restart docker
[root@tag home]#  systemctl status docker
```

# delete docker0 interface
```
yum -y install bridge-utils
ifconfig docker0 down   //docker0 172.17.0.0/20  or 192.168.0.0/20
brctl delbr docker0
```


https://blog.csdn.net/catoop/article/details/100178305



## docker容器间网络互通
- 虚拟ip   默认同一台主机的容器ip是相互可以访问的
- netowrk 容器使用自定义的network，可以通过name相互访问
- link    link的形式实现互联互通

[link](https://www.cnblogs.com/shenh/p/9714547.html)
```

```