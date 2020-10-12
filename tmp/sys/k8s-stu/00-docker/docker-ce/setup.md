
# yum
>CentOS环境下安装Docker。官方文档要求Linux kernel至少3.8以上，且docker只能运行在64位的系统中
由于RHEL6和CentOS6的内核版本为2.6
注意：建议升级linux内核，支持更多的特性

#版本差异
```
docker-engine //早期docker版本
docker-ce  //docker社区版本
docker-ee  //docker企业版本
```
#环境规划  
- centos7 2c4g
- docker-ce

[reference](https://www.cnblogs.com/xiaochina/p/11518007.html)

## 01、kernel
```
[root@mvp-dd ~]# uname -a
Linux mvp-dd 3.10.0-862.el7.x86_64 #1 SMP Fri Apr 20 16:44:24 UTC 2018 x86_64 x86_64 x86_64 GNU/Linux
[root@mvp-dd ~]# cat /etc/redhat-release 
CentOS Linux release 7.5.1804 (Core) 
```

#开启iptables对bridge的数据进行处理
```
cat > /etc/sysctl.d/docker.conf <<-'EOF'
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-ip6tables = 1
EOF

sysctl  --system
sysctl -p
```

## 02、add repo
[aliyun_repo](http://mirrors.aliyun.com/repo/)  
[alyi_doc](https://yq.aliyun.com/articles/110806 )
```
http://mirrors.aliyun.com/repo/Centos-7.repo
http://mirrors.aliyun.com/repo/epel-7.repo

yum install -y yum-utils
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
yum  clean all && yum makecache
```

## 03、install docker-ce
```
#可以查看仓库中所有docker版本，并选择特定版本安装
yum list docker-ce --showduplicates | sort -r

#安装指定版本的格式注意3:xxx 请移除3:
yum -y install docker-ce-[VERSION] 
yum install -y docker-ce-18.06.3.ce-3.el7
```
>docker-ce(server)   docker-ce-cli(cli)

## 04、docker-hub-mirrors
https://dev.aliyun.com   //注册阿里云账户  
镜像加速器  //根据相应系统，添加配置即可  

```
test ! -d /etc/docker && mkdir -p /etc/docker

cat >/etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors":["https://jnxt8d8b.mirror.aliyuncs.com"]
}
EOF

/etc/docker/daemon.json
{
  "registry-mirrors": ["http://hub-mirror.c.163.com"]
}
```

## 05、testing
systemctl enable docker && systemctl start docker && docker info
```
docker run hello-world
docker run -d ubuntu:15.10 /bin/sh -c "while true; do echo hello world; sleep 1; done"

docker version  //server /cli version info
docker info  //server info
```

## 06、uninstall docker-ce
```
docker rm -f $(docker ps -qa)
docker rmi -f $(docker images -qa)
docker system prune

[root@VM-88-143-centos ~]# systemctl stop docker
[root@VM-88-143-centos ~]# yum erase -y docker-ce-19.03.11-3.el7.x86_64  docker-ce-cli-19.03.11-3.el7.x86_64

[root@VM-88-143-centos ~]# rpm -qa |grep docker
docker-ce-cli-19.03.11-3.el7.x86_64
docker-ce-19.03.11-3.el7.x86_64

rm -rf /var/lib/docker
ifconfig docker0 down 
brcrl delbr docker0    //yum install -y bridge-utils
```

>docker支持在非root账户下使用，必须是docker组下的用户
```
groupadd docker
usermod -G docker mvpbang
su - mvpbang
docker images
```

# apt
>ubuntu 16.04/14.04

```
sudo cat /etc/sudoers
#default ubuntu nopasswd sudo
ubuntu  ALL=(ALL:ALL) NOPASSWD: ALL
```

## step 1: 安装必要的一些系统工具
```
sudo apt-get update
sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common
```

## step 2: 
```
curl -fsSL http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -
```

## Step 3: 写入软件源信息
```
sudo add-apt-repository "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"
```

## Step 4: 更新并安装 Docker-CE
```
sudo apt-get -y update
sudo apt-get -y install docker-ce
```

>注意：其他注意事项在下面的注释,安装指定版本的Docker-CE

```
#查找Docker-CE的版本:
#apt-cache madison docker-ce

#docker-ce | 17.03.1~ce-0~ubuntu-xenial | http://mirrors.aliyun.com/docker-ce/linux/ubuntu xenial/stable amd64 Packages
#docker-ce | 17.03.0~ce-0~ubuntu-xenial | http://mirrors.aliyun.com/docker-ce/linux/ubuntu xenial/stable amd64 Packages

#安装指定版本的Docker-CE
(VERSION 例如上面的 17.03.1~ce-0~ubuntu-xenial)

#sudo apt-get -y install docker-ce=[VERSION]

ubuntu@VM-95-113-ubuntu:~$ sudo apt-get install -y docker-ce=19.03.11~3-0~ubuntu-xenial
```

## Step 5: adduser && docker-hub
```
usermod -aG docker ubuntu  //附加组docker
newgrp docker  //切换到docker群组，前提是在docker组中
docker images
```

# binanry

#部署平台  
centos7.x  
docker-18.9.0 x86_64  static //二进制

#reference  
https://github.com/docker/docker-ce/tags

#启动docker-server //最简单粗糙  
```
nohup dockerd &>/tmp/dk.log &

docker run  --rm -h lab-mysql --name mysql \
	-e MYSQL_ROOT_PASSWORD=123123 \
	-v /dk_data/mysql/data:/var/lib/mysql \
	-v /dk_data/mysql/cnf:/etc/mysql/conf.d \
	-p 3306:3306 \
	-d mysql:5.5

docker start|stop mysql
docker run --rm  --name mysql  -e MYSQL_ROOT_PASSWORD=123123 -p 3306:13306 -d mysql:5.6
```

## Step 1: download docker-ce
https://download.docker.com/linux/static/stable/x86_64/

## Step 2: kernel check
```
[root@lab-210 ~]# uname -r     
//kerner3.10.+   centois7.x 内核都是3.10+，此举只是为了验证
3.10.0-862.el7.x86_64
```

## Step 3: setup
>docker-18.06.1-ce.tgz  

`
tar zxf docker-18.06.1-ce.tgz && mv docker/* /usr/bin/ && rm -rf docker*.tgz`

>解压切移动binary到/usr/bin

## Step 4: docer.service
```
vim /usr/lib/systemd/system/docker.service

[Unit]
Description=Docker Application Container Engine
Documentation=https://docs.docker.com
After=network-online.target firewalld.service
Wants=network-online.target

[Service]
Type=notify
# the default is not to use systemd for cgroups because the delegate issues still
# exists and systemd currently does not support the cgroup feature set required
# for containers run by docker
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock
ExecReload=/bin/kill -s HUP $MAINPID
# Having non-zero Limit*s causes performance problems due to accounting overhead
# in the kernel. We recommend using cgroups to do container-local accounting.
LimitNOFILE=infinity
LimitNPROC=infinity
LimitCORE=infinity
# Uncomment TasksMax if your systemd version supports it.
# Only systemd 226 and above support this version.
#TasksMax=infinity
TimeoutStartSec=30
# set delegate yes so that systemd does not reset the cgroups of docker containers
Delegate=yes
# kill only the docker process, not all processes in the cgroup
KillMode=process
# restart the docker process if it exits prematurely
Restart=on-failure
StartLimitBurst=3
StartLimitInterval=60s
  
[Install]
WantedBy=multi-user.target
```
> ExecStart=/usr/bin/dockerd --graph /data/docker  //--graph设置docker_root存储目录，默认/var/lib/docker

## Step 5: open bridge
#开启iptables对bridge的数据进行处理
```
cat > /etc/sysctl.d/docker.conf <<-'EOF'
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-ip6tables = 1
EOF

sysctl  --system
sysctl -p
```
## Step 6： add-docker-hub && start testing
#docker添加国内docker-hub源
```
test ! -d /etc/docker && mkdir -p /etc/docker

cat >/etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors":["https://jnxt8d8b.mirror.aliyuncs.com"]
}
EOF
```

```
systemctl daemon-reload
systemctl start docker
systemctl enable docker
```

```
#testing  docker
systemctl status docker
docker info
docker run --rm -it centos:5 bash
```

# online-setup
#官方脚本国内源  
`
curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
`
>curl参数 -f --fail(请求资源失败，仅仅反馈状态码) -s --silent(静默) -S --show-error(仅在出错时显示错误)
## 01、docker-hub-mirrors
```
test ! -d /etc/docker && mkdir -p /etc/docker
/etc/docker/daemon.json
{
  "registry-mirrors": ["http://hub-mirror.c.163.com"]
}
```

## 02、start-docker-ce 
`
systemctl enable docker && systemctl start docker
`
## docker-ce online sh setup
```
curl -fsSL https://get.daocloud.io/docker | sh
curl -sSL https://get.docker.com/ | sh   //官方在线安装docker
```
