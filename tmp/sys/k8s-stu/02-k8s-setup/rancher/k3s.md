# k3s
https://k3s.io/

https://github.com/rancher/k3s

https://github.com/rancher/k3s/releases 

## quick_express
```
curl -sfL https://get.k3s.io | sh -

#Check for Ready node, takes maybe 30 seconds
k3s kubectl get node
```
>国内你懂得，太难了。还是离线搞吧

[基于centos7.6离线部署开k3s](https://www.cnblogs.com/xiaochina/p/11563680.html)

## env
- docker-ce
- k3s
- centos7.x

>使用docker-cri,默认container

## step1: docker-ce
pass

## step2 k3s
#k8s-v1.18.4 + k3s
[link](https://github.com/rancher/k3s/releases/tag/v1.18.4%2Bk3s1)

#k3s images
```
https://github.com/rancher/k3s/releases/download/v1.18.4%2Bk3s1/k3s-airgap-images-amd64.tar
docker load -i k3s-airgap-images-amd64.tar   //导入离线镜像
```

#install k3s binary && install k3s
```
https://github.com/rancher/k3s/releases/download/v1.18.4%2Bk3s1/k3s  //k3s binary  
https://raw.githubusercontent.com/rancher/k3s/master/install.sh  //k3s安装脚本,很多变量定义

wget --no-check-certificate https://raw.githubusercontent.com/rancher/k3s/master/install.sh

chmod +x k3s install.sh
mv k3s /usr/bin/

#isntall
export INSTALL_K3S_SKIP_DOWNLOAD=true      //设置跳过下载k3s二进制文件
export INSTALL_K3S_BIN_DIR=/usr/bin       //设置k3s安装目录
./install.sh       //自动建立service服务及软连接....

systemctl status k3s  //服务运行状态
journalctl -u k3s -f  //根据日志可以看到服务启动不起来,要去国外拉images，你懂得，heihei
```

#modify k3s CRI used docker
```
vim /etc/systemd/system/k3s.service
ExecStart=/usr/bin/k3s \
server --docker\     //容器选择docker，替换默认的containerd

systemctl daemon-reload && systemctl restart k3s
```

#check k3s
```
[root@c-3-105 ~]# kubectl get nodes
NAME      STATUS   ROLES    AGE     VERSION
c-3-105   Ready    master   4m47s   v1.18.4+k3s1
[root@c-3-105 ~]# 
[root@c-3-105 ~]# kubectl get cs
NAME                 STATUS    MESSAGE   ERROR
scheduler            Healthy   ok        
controller-manager   Healthy   ok  
[root@c-3-105 ~]# kubectl get pods -A
[root@c-3-105 ~]# 
```
