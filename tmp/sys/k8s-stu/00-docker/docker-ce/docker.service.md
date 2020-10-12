
1、建立docker_root目录
mkdir  /export/docker  -p   //修改默认docker-root目录/var/lib/docker

2.修改docker配置文件
#vim /etc/sysconfig/docker
OPTIONS='--graph=/export/docker -b=dockerbr0'   //选择自定义网桥

3.配置dockerbr0网桥
# vim /etc/sysconfig/network-scripts/ifcfg-dockerbr0 
DEVICE=dockerbr0 
TYPE=Bridge 
ONBOOT=yes 
BOOTPROTO=static 
PREFIX=24 
IPADDR=192.168.254.1 
NETMASK=255.255.255.0 
DELAY=0 
NAME=dockerbr0

4.打开内核端口转发
#vim /etc/sysctl.conf
net.ipv4.ip_forward = 1
#sysctl -p

###启动服务默认虚拟出docker0网卡   172.17.0.1/16
[root@k8s-001 docker]# ip a    //   == ip addr
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host 
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether fa:16:3e:9e:f6:40 brd ff:ff:ff:ff:ff:ff
    inet 192.168.0.53/24 brd 192.168.0.255 scope global noprefixroute dynamic eth0
       valid_lft 57849sec preferred_lft 57849sec
    inet6 fe80::f816:3eff:fe9e:f640/64 scope link 
       valid_lft forever preferred_lft forever
3: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default 
    link/ether 02:42:28:4c:75:91 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:28ff:fe4c:7591/64 scope link 
       valid_lft forever preferred_lft forever


ExecStart=/usr/bin/dockerd -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375
[root@master ~]# docker -H 192.168.63.200:2375 images   //远程查看对应daemon的images

###docke-daemon配置
/etc/sysconfig/docker
OPTIONS='-H=unix:///var/run/docker.sock -H=tcp://0.0.0.0:2375'

OPTIONS用来控制Docker Daemon进程参数
-H 表示Docker Daemon绑定的地址， -H=unix:///var/run/docker.sock  -H=tcp://0.0.0.0:2375
--registry-mirror表示Docker Registry的镜像地址--registry-mirror=http://4bc5abeb.m.daocloud.io
--insecure-registry表示（本地）私有Docker  Registry的地址，  --insecure-registry  ${pivateRegistyHost}:5000
--selinux-enabled是否开启SELinux，默认开启  --selinux-enabled=true
--bip 表示网桥docker0使用指定CIDR网络地址，--bip=172.17.42.1
-b  表示采用已经创建好的网桥， -b=xxx

代理的设置
http_proxy=xxxxx:8080
https_proxy=xxxxxx:8080

systemctl restart docker 
ps -ef |grep docker
ss -lnt |grep 2375

journalctl -u docker -f  // == /var/log/message 对unit服务查看日志



```
vi /usr/lib/systemd/system/docker.service
root@k8s-001 docker]# cat /usr/lib/systemd/system/docker.service 
[Unit]
Description=Docker Application Container Engine
Documentation=http://docs.docker.com
After=network.target
Wants=docker-storage-setup.service
Requires=docker-cleanup.timer

[Service]
Type=notify
NotifyAccess=main
EnvironmentFile=-/run/containers/registries.conf
EnvironmentFile=-/etc/sysconfig/docker            //环境变量文件读取
EnvironmentFile=-/etc/sysconfig/docker-storage
EnvironmentFile=-/etc/sysconfig/docker-network
Environment=GOTRACEBACK=crash
Environment=DOCKER_HTTP_HOST_COMPAT=1
Environment=PATH=/usr/libexec/docker:/usr/bin:/usr/sbin
ExecStart=/usr/bin/dockerd-current \
          --add-runtime docker-runc=/usr/libexec/docker/docker-runc-current \
          --default-runtime=docker-runc \
          --exec-opt native.cgroupdriver=systemd \
          --userland-proxy-path=/usr/libexec/docker/docker-proxy-current \
          --init-path=/usr/libexec/docker/docker-init-current \
          --seccomp-profile=/etc/docker/seccomp.json \
          $OPTIONS \
          $DOCKER_STORAGE_OPTIONS \
          $DOCKER_NETWORK_OPTIONS \
          $ADD_REGISTRY \
          $BLOCK_REGISTRY \
          $INSECURE_REGISTRY \
	  $REGISTRIES
ExecReload=/bin/kill -s HUP $MAINPID
LimitNOFILE=1048576
LimitNPROC=1048576
LimitCORE=infinity
TimeoutStartSec=0
Restart=on-abnormal
KillMode=process

[Install]
WantedBy=multi-user.target
```