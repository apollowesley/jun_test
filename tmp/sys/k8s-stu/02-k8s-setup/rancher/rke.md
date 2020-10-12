# RKE
https://github.com/rancher/rke

https://docs.rancher.cn/  
https://docs.rancher.cn/rke/  

http://mirror.cnrancher.com/  

# setup on centos7
# env
```
master 192.168.3.50
work   192.168.3.51
rke
```
## step1: precheck
```
#hostname
hostnamectl  set-hostname c-3-50  //dns规则

#selinux 

#firewalld

#swap
[root@c-3-50 ~]# swapoff -a
[root@c-3-50 ~]# vi /etc/fstab 
[root@c-3-50 ~]# mount -a
```

## step2: install docker-ce
https://github.com/rancher/install-docker

[root@localhost ~]# wget -k https://github.com/rancher/install-docker/blob/master/18.06.2.sh?raw=true

## step3: rke
http://rancher-mirror.cnrancher.com/rke/v1.1.3/rke_linux-amd64


rke up --config ./rancher-cluster.yml


## reset all nodes
```
df -h|grep kubelet |awk -F % '{print $2}'|xargs umount
sudo rm /var/lib/kubelet/* -rf
sudo rm /etc/kubernetes/* -rf
sudo rm /etc/cni/* -rf
sudo rm /var/lib/rancher/* -rf
sudo rm /var/lib/etcd/* -rf
sudo rm /var/lib/cni/* -rf
sudo rm /opt/cni/* -rf
sudo ip link del flannel.1
ip link del cni0
iptables -F && iptables -t nat -F
docker ps -a|awk '{print $1}'|xargs docker rm -f
docker volume ls|awk '{print $2}'|xargs docker volume rm
systemctl restart docker
```

https://www.kubernetes.org.cn/4004.html


http://www.iigrowing.cn/?p=6502


https://blog.csdn.net/login_sonata/article/details/93847888

## rke on ubuntu16.04
### env
```
master 192.168.3.60
work   192.168.3.61
rke
ubuntu 16.04
```
### step1: precheck
```
#ssh && group
ssh-keygen -t rsa -P ''  //master
ubuntu@u-3-60:~$ ssh-copy-id 192.168.3.60  //master
ubuntu@u-3-60:~$ ssh-copy-id 192.168.3.61  //work
//testing no passwd ssh
ubuntu@u-3-60:~$ ssh 192.168.3.60 date
Sun Jul 12 23:06:00 CST 2020
ubuntu@u-3-60:~$ 
ubuntu@u-3-60:~$ ssh 192.168.3.61 date
Sun Jul 12 23:06:03 CST 2020
注意：master可以ssh所有节点，免密

sudo groupadd docker && sudo usermod -aG docker ubuntu  //all nodes
```

#dis ufw
```
sudo ufw disable
sudo ufw status
```

#dis swap   //all nodes
```
swapoff -a
/etc/fstab
mount -a
```
#check modules  //all nodes
```
ubuntu@u-3-60:~$ cat /etc/modules
# /etc/modules: kernel modules to load at boot time.
#
# This file contains the names of kernel modules that should be loaded
# at boot time, one per line. Lines beginning with "#" are ignored.

sudo su - 

tee >>/etc/modules <<EOF
##add k8s
br_netfilter
ip6_udp_tunnel
ip_set
ip_set_hash_ip
ip_set_hash_net
iptable_filter
iptable_nat
iptable_mangle
iptable_raw
nf_conntrack_netlink
nf_conntrack
nf_conntrack_ipv4
nf_defrag_ipv4
nf_nat
nf_nat_ipv4
nf_nat_masquerade_ipv4
nfnetlink
udp_tunnel
veth
vxlan
x_tables
xt_addrtype
xt_conntrack
xt_comment
xt_mark
xt_multiport
xt_nat
xt_recent
xt_set
xt_statistic
xt_tcpudp
EOF

//reboot ubuntu to apply

//check
module_list='br_netfilter ip6_udp_tunnel ip_set ip_set_hash_ip ip_set_hash_net iptable_filter iptable_nat iptable_mangle iptable_raw nf_conntrack_netlink nf_conntrack nf_conntrack_ipv4 nf_defrag_ipv4 nf_nat nf_nat_ipv4 nf_nat_masquerade_ipv4 nfnetlink udp_tunnel veth vxlan x_tables xt_addrtype xt_conntrack xt_comment xt_mark xt_multiport xt_nat xt_recent xt_set xt_statistic xt_tcpudp'

for module in $module_list;
do
      if ! lsmod | grep -q $module; then
            echo "module $module is not present"
      fi
done;
```

#add sysctl  //all nodes
```
sudo su -

cat >/etc/sysctl.d/k8s.conf <<EOF
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-ip6tables = 1
net.ipv4.ip_forward = 1
EOF

sysctl --system
```

### step2: install docker-ce
#docker-ce //all nodes
```
wget https://releases.rancher.com/install-docker/18.09.2.sh
chmod +x 18.09.2.sh
./18.09.2.sh

curl -sSL https://releases.rancher.com/install-docker/18.09.2.sh | sh

docker version --format '{{.Server.Version}}'    //check version
```

#docker-hub china
```
sudo su -

cat >> /etc/docker/daemon.json <<'EOF'
{
  "registry-mirrors": ["https://ierl59hw.mirror.aliyuncs.com"]
}
EOF

sudo systemctl daemon-reload && systemctl restart docker
```

### step3: install rke
```
https://github.com/rancher/rke/tags
https://github.com/rancher/rke/releases/tag/v1.1.3  //不同版本支持k8s版本也是不同的
https://github.com/rancher/rke/releases/download/v1.1.3/rke_linux-amd64

chmod +x rke_linux-amd64 && mv rke_linux-amd64 rke && sudo mv rke /usr/bin/

ubuntu@u-3-60:~$ rke --version
rke version v1.1.3
```

### step4: rke config
#cluster.yml  //最简单配置,默认images最新的版本
```
nodes:
    - address: 192.168.3.60
      user: ubuntu
      role:
        - controlplane
        - etcd
        - worker
    - address: 192.168.3.61
      user: ubuntu
      role:
        - controlplane
        - etcd
        - worker
```

#rke up help
```
ubuntu@u-3-60:~$ rke up  --help
NAME:
   rke up - Bring the cluster up

USAGE:
   rke up [command options] [arguments...]

OPTIONS:
   --config value               Specify an alternate cluster YAML file (default: "cluster.yml") [$RKE_CONFIG]
   --local                      Deploy Kubernetes cluster locally
   --dind                       Deploy Kubernetes cluster in docker containers (experimental)
   --dind-storage-driver value  Storage driver for the docker in docker containers (experimental)
   --dind-dns-server value      DNS resolver to be used by docker in docker container. Useful if host is running syst
emd-resovld (default: "8.8.8.8")   --update-only                Skip idempotent deployment of control and etcd plane
   --disable-port-check         Disable port check validation between nodes
   --init                       Initiate RKE cluster
   --cert-dir value             Specify a certificate dir path
   --custom-certs               Use custom certificates from a cert dir
   --ssh-agent-auth             Use SSH Agent Auth defined by SSH_AUTH_SOCK
   --ignore-docker-version      Disable Docker version check
```

#rke up k8s
```
rke up --ignore-docker-version --config ./cluster.yml

ubuntu@u-3-60:~$ ls -l
total 124
-rw-r----- 1 ubuntu ubuntu 111110 Jul 13 00:13 cluster.rkestate  //rke status
-rw-rw-r-- 1 ubuntu ubuntu    235 Jul 12 23:49 cluster.yml   //rke
-rw-r----- 1 ubuntu ubuntu   5387 Jul 13 00:13 kube_config_cluster.yml  //kubeconfig
```

### step5: kubectl
```
ubuntu@u-3-60:~$ docker ps |grep kube:  //kubernetes version   v1.18.3  //kubectl 主线版本一致 v1.18.x

http://rancher-mirror.cnrancher.com/kubectl/v1.18.3/linux-amd64-v1.18.3-kubectl

chmod +x linux-amd64-v1.18.3-kubectl && mv linux-amd64-v1.18.3-kubectl kubectl && sudo mv kubectl /usr/bin/
mkdir .kube && cp kube_config_cluster.yml  .kube/config

ubuntu@u-3-60:~$ kubectl get cs
NAME                 STATUS    MESSAGE             ERROR
scheduler            Healthy   ok                  
etcd-0               Healthy   {"health":"true"}   
etcd-1               Healthy   {"health":"true"}   
controller-manager   Healthy   ok                  
ubuntu@u-3-60:~$ kubectl get nodes
NAME           STATUS   ROLES                      AGE   VERSION
192.168.3.60   Ready    controlplane,etcd,worker   18m   v1.18.3
192.168.3.61   Ready    controlplane,etcd,worker   18m   v1.18.3

#kubectl
sudo apt install -y bash-completion
kubectl completion bash > ~/.kube/completion.bash.inc

tee >>~/.bashrc <<EOF
#add kubectl 
source ~/.kube/completion.bash.inc
EOF

source ~/.bashrc
```