# docker-hub offical
#官方dockerhub库  
https://hub.docker.com/  
https://quay.io/

>Access the world’s largest library of container images

## china-internet-LTD
#aliyun-docker  
https://dev.aliyun.com/
https://hub.daocloud.io/

https://mirror.ccs.tencentyun.com/  //腾讯

https://www.jianshu.com/p/bf29baf97dea
```
#daocloud docker-hub加速
curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s

```
https://dev.aliyun.com/--> 管理中心-->容器镜像服务-->镜像加速器  

https://ierl59hw.mirror.aliyuncs.com

#Docker客户端版本大于 1.10.0 的用户
修改daemon配置文件/etc/docker/daemon.json来使用加速器
```
mkdir -p /etc/docker
cat >> /etc/docker/daemon.json <<'EOF'
{
  "registry-mirrors": ["https://ierl59hw.mirror.aliyuncs.com"]
}
EOF

[root@test ~]# cat /etc/docker/daemon.json   //docker 1.11.2 +支持
{"registry-mirrors": ["http://681a96df.m.daocloud.io"],
    "live-restore": true
}
```

```
systemctl daemon-reload
systemctl restart docker       //ubuntu-aliyun
```

```
[root@lab-90 ~]# docker info |tail   //查看是否使用镜像服务器
Debug Mode (server): false
Registry: https://index.docker.io/v1/
Labels:
Experimental: false
Insecure Registries:
 127.0.0.0/8
Registry Mirrors:
 https://ierl59hw.mirror.aliyuncs.com/   //已经切换到阿里云内网镜像仓库
Live Restore Enabled: false
```

docker pull registry
docker save -o registry.tar registry //docker save registry >registry.tar 打包registry容器

//导入镜像
docker load <registry.tar

//ERROR  
```unable to ping registry endpoint https://172.18.3.22:5000/v0/
v2 ping attempt failed with error: Get https://172.18.3.22:5000/v2/: http: server gave HTTP response to HTTPS client
这是由于Registry为了安全性考虑，默认是需要https证书支持的.但是我们可以通过一个简单的办法解决:
修改/etc/docker/daemon.json文件
#vi /etc/docker/daemon.json
{
    "insecure-registries": ["<ip>:5000"] 
}
#systemctl daemon-reload 
#systemctl restart docker
```
>注：<ip>：Registry的机器ip地址，在安装registry的节点和客户端需要访问私有Registry的节点都需要执行此步操作


## docker images 加速
[link](https://www.cnblogs.com/flhs/p/12383895.html)

- gcr.io
- quay.io
- docker.io

>k8s.gcr.io等价于gcr.io/google-containers

registry.aliyuncs.com/google_containers

#中科大mirrors  
https://github.com/ustclug/mirrorrequest
 
#中科大加速 gcr.io
```
docker pull gcr.io/xxx/yyy:zzz
docker pull gcr.mirrors.ustc.edu.cn/xxx/yyy:zzz

docker pull k8s.gcr.io/xxx:yyy
docker pull gcr.io/google-containers/xxx:yyy

docker pull gcr.mirrors.ustc.edu.cn/google-containers/xxx:yyy

docker pull gcr.mirrors.ustc.edu.cn/kubernetes-helm/tiller:v2.9.1
docker tag  gcr.mirrors.ustc.edu.cn/kubernetes-helm/tiller:v2.9.1 gcr.io/kubernetes-helm/tiller:v2.9.1
```

#中科大加速 quay.io
```
docker pull quay.io/xxx/yyy:zzz
docker pull quay.mirrors.ustc.edu.cn/xxx/yyy:zzz

docker pull quay.mirrors.ustc.edu.cn/coreos/kube-state-metrics:v1.5.0
docker tag quay.mirrors.ustc.edu.cn/coreos/kube-state-metrics:v1.5.0 quay.io/coreos/kube-state-metrics:v1.5.0
```

#docker.io
```
hub-mirror.c.163.com
daocloud.io

docker pull xxx:yyy
docker pull docker.mirrors.ustc.edu.cn/library/xxx:yyy
```