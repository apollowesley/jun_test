# rancher
https://rancher.com/  
https://www.rancher.cn/  
https://www.rancher.cn/training/
***
https://www.rancher.cn/quick-start

#docs  
https://docs.rancher.cn/  
https://rancher.com/docs

***
https://forums.cnrancher.com/ #论坛

#centos升级内核及安装rancher:stable
https://blog.csdn.net/qq_39440086/article/details/94590475

https://blog.csdn.net/bbwangj/article/details/81231118
https://blog.csdn.net/bbwangj/article/details/82025010


## rancher-mirrors
```
http://mirror.cnrancher.com/
```

## rancher cleanup
```
#清理脚本
wget https://raw.githubusercontent.com/xiliangMa/xiliangMa.github.io/master/rancher/rancher-clean.sh

#执行权限:
chmod +x rancher-clean.sh

#清理
./rancher-clean.sh

=======
# ！！！下面这两个命令会删除机器上所有容器（请慎重！你可以手工挨个删除或修改一下命令过滤条件）
docker rm -f $(sudo docker ps -aq);
docker volume rm $(sudo docker volume ls -q);

rm -rf /etc/cni \
       /etc/kubernetes \
       /opt/cni \
       /opt/rke \
       /run/secrets/kubernetes.io \
       /run/calico \
       /run/flannel \
       /var/lib/calico \
       /var/lib/etcd \
       /var/lib/cni \
       /var/lib/kubelet \
       /var/lib/rancher/rke/log \
       /var/log/containers \
       /var/log/pods \
       /var/run/calico

for mount in $(mount | grep tmpfs | grep '/var/lib/kubelet' | awk '{ print $3 }') /var/lib/kubelet /var/lib/rancher; do umount $mount; done

rm -f /var/lib/containerd/io.containerd.metadata.v1.bolt/meta.db
sudo systemctl restart containerd
sudo systemctl restart docker

# 根据你的需要决定是否 reboot 重启机器，如果机器比较干净，最好重启一下

```

## rancher 1.x
```
docker run -d --name rancher-server --restart=always -p 8181:8080 rancher/server:stable 
```

## step0: env
- 2c4g linux
- online internet
- docker-ce

## step1 docker-ce
#turn bridge
```
#bridge
cat > /etc/sysctl.d/docker.conf <<-'EOF'
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-ip6tables = 1
EOF

sysctl  --system
sysctl -p

#docker-ce
yum install -y yum-utils
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
yum  clean all && yum makecache
```

#install docker-ce version
```
yum list docker-ce --showduplicates | sort -r
#安装指定版本的格式注意3:xxx 请移除3:
yum -y install docker-ce-[VERSION] 
yum install -y docker-ce-18.06.3.ce-3.el7
```

#add china docker-hub
```
test ! -d /etc/docker && mkdir -p /etc/docker

cat >/etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors":["https://jnxt8d8b.mirror.aliyuncs.com"]
}
EOF
```
#enable reboot start && start docker
```
systemctl enable docker && systemctl start docker && docker info |grep -C 5 Mirror
```

#testing docker
```
[root@c-3-105 ~]# docker run --rm -it centos:5 cat /etc/hosts
Unable to find image 'centos:5' locally
5: Pulling from library/centos
38892065247a: Pull complete 
Digest: sha256:70fffd687ff9545662c30f9043108489c698662861cd5f76070f7e2cd350564f
Status: Downloaded newer image for centos:5
127.0.0.1	localhost
::1	localhost ip6-localhost ip6-loopback
fe00::0	ip6-localnet
ff00::0	ip6-mcastprefix
ff02::1	ip6-allnodes
ff02::2	ip6-allrouters
172.17.0.2	d1f3bd9d17d8
[root@c-3-105 ~]# 
```

## step2: rancher-sever
#版本及系统支持  
https://rancher.com/support-maintenance-terms/all-supported-versions

#run rancher-server
```
//master-nodes
docker run -d --restart=unless-stopped -p 80:80 -p 443:443 rancher/rancher:v2.3.5

#rancher-data
mkdir /opt/rancher_data

docker run -d --restart=unless-stopped \
    -v /opt/rancher_data:/var/lib/rancher \
    -p 80:80 -p 443:443 rancher/rancher:stable
```

#rancher single 
>内置kube-apiservers
```
docker ps |grep rancher

docker exec -it xxx bash
kubectl cluster-info
kubectl get all -A
kubectl get crd
```

## step3： testing on web
```
https://192.168.3.105/
admin/admin  //set admin passwd
```

## import cluster k3s 
k3s setup passed  
[install_k3s](https://gitee.com/m0p/k8s-stu/blob/master/02-k8s-setup/rancher/k3s.md)


https://www.cnblogs.com/kingle-study/p/10338534.html

# rancher-nfs
[Rancher2.0 外置存储卷nfs](https://www.cnblogs.com/kingle-study/p/10463022.html)




## rancher-cli
```
rancher login https://xxxx/v3 --token token-pz9qg:6lwlb97tsqdbcdc9vn8v6td2cdhdqvcnkw4qpw8clbzmdcv57m94jg 
```


## rancher-eks
https://www.infoq.cn/article/uRRVLG2FRuuRjWvi54cr
https://www.cnblogs.com/yuezhimi/p/11118750.html


## rancher + nfs-storage-class +helm
https://github.com/kubernetes-incubator/external-storage/tree/master/nfs-client

https://blog.csdn.net/jerryhu1234/article/details/82890894

https://www.cnblogs.com/klvchen/p/13234779.html
https://blog.csdn.net/engchina/article/details/97397505
https://blog.csdn.net/weixin_33868027/article/details/92926567



## helm install rancher
https://cloud.tencent.com/developer/article/1422807